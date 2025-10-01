package Bac_Bo;

import Perfil.*;
import Auxiliar.*;
import Bac_Bo.Recursos.Dados;
import View.*;

import java.util.Random;
import java.util.Scanner;
public class Bac_Bo {
    private Scanner sc;
    public Jogador jogador;
    private int fichasApostadas = 0;
    private Random rand = new Random();
    private boolean acabou = false;

    public Bac_Bo(Scanner sc, Jogador jogador){
        this.sc = sc;
        this.jogador = jogador;
    }

    public void iniciar() throws InterruptedException{

        while(!acabou){
            acabou = iniciarRodada();
        }
    }

    public boolean iniciarRodada() throws InterruptedException{
        if (jogador.getBancaReais()==0){
            System.out.println("Você zerou a banca. Saindo do Bac-Bo... (Pressione Enter para continuar):");
            sc.nextLine();
            return true;
        }       
        
        // Escolha da continuidade e valor a se apostar
        Control.limparTerminal();
        Control.printBackBo();
        System.out.println("Valor da banca: " + jogador.getBancaReais());
        System.out.println("Caso deseje parar de jogar, aposte 0.");
        // InputLerValorCentatos ja devolve em centavos
        fichasApostadas = Input.lerValorEmCentavos(sc, "Digite a quantidade de fichas para apostar: ");
        if(fichasApostadas == 0){
            return true;
        }
        while(fichasApostadas < 0 || fichasApostadas > jogador.getBancaCentavos()){
            fichasApostadas = Input.lerValorEmCentavos(sc, "Aposta inválida. Tente novamente: ");
        }
        // Verifica se o valor apostado é válido e subtrai da banca
        jogador.setBanca(jogador.getBancaCentavos() - fichasApostadas);

        continuacaoDaRodada();
        

        return false;
    }

    private void continuacaoDaRodada() throws InterruptedException{
        
        // Primeira rolagem
        Control.limparTerminal();
        System.out.println("Rodando o PRIMEIRO LANCE de dados... (Pressione Enter para continuar):");
        sc.nextLine();
        sc.nextLine();

        // Roda a animação do primeiro lance
        rodarDados(rand, null);
        
        // Obtém o resultado do primeiro lance
        ResultadoDados primeiroResultado = resultadoDados(rand);

        // Exibe o primeiro resultado
        Control.limparTerminal();
        mostrarResultado(primeiroResultado, "PRIMEIRO LANCE (Jogador vs Dealer):");
        
        System.out.println("\nPrimeiro resultado obtido. Pressione Enter para o SEGUNDO lance de dados...");
        sc.nextLine();
        
        // Segunda rolagem 
        // Roda a animação do segundo lance, mantendo o primeiro resultado na tela
        rodarDados(rand, primeiroResultado);
        
        // Obtém o resultado do segundo lance
        ResultadoDados segundoResultado = resultadoDados(rand);
        
        // Exibição final 
        Control.limparTerminal();
        System.out.println("--- Resultados Finais Consolidados ---");
        
        // Soma os resultados
        int placarJogador = primeiroResultado.dadoJogador() + segundoResultado.dadoJogador();
        int placarDealer = primeiroResultado.dadoDealer() + segundoResultado.dadoDealer();
        
        // Exibe o primeiro lance
        mostrarResultado(primeiroResultado, "PRIMEIRO LANCE:");
        
        System.out.println("\n----------------------------------------\n");
        
        // Exibe o segundo lance
        mostrarResultado(segundoResultado, "SEGUNDO LANCE:");
        
        System.out.println("\n========================================");
        System.out.printf("PLACAR FINAL: Jogador = %d | Dealer = %d\n", placarJogador, placarDealer);
        System.out.println("========================================\n");
        
        encerramentoEpagamento(placarJogador, placarDealer);
        
        System.out.println("Rodada concluída. Pressione Enter para continuar...");
        sc.nextLine();
    }

        private void encerramentoEpagamento(int placarJogador, int placarDealer){
        String mensagemResultado;

        if (placarJogador > placarDealer) {
            // jogador ganhou
            jogador.setBanca(jogador.getBancaCentavos() + fichasApostadas * 2); 
            mensagemResultado = "     JOGADOR GANHOU!      ";
        } else if (placarJogador < placarDealer) {
            // casa ganhou
            mensagemResultado = "       CASA GANHOU!       ";
        } else {
            // empate
            jogador.setBanca(jogador.getBancaCentavos() + fichasApostadas);
            mensagemResultado = "          EMPATE!           ";
        }

        System.out.println("\n--- RESULTADO DA RODADA ---");
        System.out.println(mensagemResultado);
        System.out.println("---------------------------");
    }

    // O dado da esquerda é do Jogador, o da direita é do Dealer.
    private void rodarDados(Random rand, ResultadoDados resultadoAnterior) throws InterruptedException{
        for (int i = 0; i < 15; i++) {
            int f1 = rand.nextInt(Dados.frames.length); 
            int f2 = rand.nextInt(Dados.frames.length);

            Control.limparTerminal(); 
            
            // Se houver um resultado anterior, exibe em cima
            if (resultadoAnterior != null) {
                mostrarResultado(resultadoAnterior, "PRIMEIRO LANCE (Jogador vs Dealer):");
                System.out.println("\n----------------------------------------\n");
            }
            
            Dados.printSideBySide(Dados.frames[f1], Dados.frames[f2], 30);
            Thread.sleep(120 + rand.nextInt(100));
        }
    }

    private ResultadoDados resultadoDados(Random rand){
        
        int resultadoJogador = rand.nextInt(6)+1; // Dado Jogador (Esquerda)
        int resultadoDealer = rand.nextInt(6)+1; // Dado Dealer (Direita)
        
        return new ResultadoDados(resultadoJogador, resultadoDealer);
    }
    
    //  Exibi o resultado
    private void mostrarResultado(ResultadoDados resultado, String titulo) {
        if (!titulo.isEmpty()) {
            System.out.println(titulo);
        }

        Dados.printSideBySide(Dados.faces[resultado.dadoJogador()-1], Dados.faces[resultado.dadoDealer()-1], 30);
        System.out.printf("Dado Jogador: %d | Dado Dealer: %d\n", resultado.dadoJogador(), resultado.dadoDealer());
    }

    // Record para retornar dois valores de uma vez
    private static record ResultadoDados(int dadoJogador, int dadoDealer) {}
}
