package Blackjack;

import Blackjack.Recursos.*;
import Perfil.*;
import Auxiliar.*;
import View.*;

import java.util.Scanner;

public class Blackjack {
    Scanner sc;
    Jogador jogador;
    Dealer dealer = new Dealer("Dealer");
    Baralho baralho = new Baralho();
    int fichasApostadas = 0;
    boolean acabou = false;
    
    public Blackjack(Scanner sc,Jogador jogador){
        this.sc = sc;
        this.jogador = jogador;
    }
    
    public void iniciar(Scanner sc){
        Control.limparTerminal();
        // Enquanto o jogo não retornar true a rodada não acabou
        while(!acabou){
            acabou = incioRodada();
        }
        
        System.out.println("Saindo do Blackjack... (Pressione Enter para continuar):");
        sc.nextLine();
        sc.nextLine();
    }

    private boolean incioRodada(){
        baralho.resetBaralho();
        jogador.limparMao();
        dealer.limparMao();
        
        // se ao acabar a rodada ele zerou a banca o jogo encerra altomaticamente
        if(jogador.getBancaReais() == 0){
            return true;
        }

        // Escolha da continuidade e valor a se apostar
        Control.limparTerminal();
        Control.printBlackjack();
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
        
        // Continuação da Rodada
        continuacaoDaRodada();

        // Encerramento da rodada
        Encerramento_E_Pagamento(sc);


        return false;
    }

    private void continuacaoDaRodada(){

        //Distribuição de cartas
        jogador.adicionarCarta(baralho.puxCarta());
        jogador.adicionarCarta(baralho.puxCarta());
        dealer.adicionarCarta(baralho.puxCarta());
        dealer.adicionarCarta(baralho.puxCarta());
        dealer.getMao().getCartas().get(0).setVirado(true); 
        imprimirMaos();

        // Momento que o jogador realiza suas ações
        acaoJogagor(sc);
        // Se depois de jogar ele estourar, retorna para a escolha de contunuidade no jogo
        if(jogador.valorMao() > 21){
            return;
        }
        // Se ele não estourou o dealer puxa as cartas dele
        acaoDealer(sc);

    }

    private void acaoJogagor(Scanner sc){
        boolean parou = false;
        while(!parou){
            //verifica se estourou
            if(jogador.valorMao() > 21){
                return;
            }
            // Seleciona a ação do player
            System.out.println("Escolha sua ação: 1 - Puxar; 2 - Parar; 3 - Dobrar");
            String mensagem = "Selecione sua ação: ";
            int escolha = Input.lerInt(sc, mensagem, 1, 3);

            // Age de acordo com a escolha feita
            switch (escolha) {
                case 1:
                    jogador.adicionarCarta(baralho.puxCarta());
                    imprimirMaos();
                    break;
                
                case 2:
                    parou = true;
                    break;
                case 3:
                    if(fichasApostadas > jogador.getBancaCentavos()){
                        System.out.println("Não é possível dobrar agora.");
                    } else{
                        jogador.setBanca(jogador.getBancaCentavos()-fichasApostadas);
                        fichasApostadas *= 2;
                        jogador.adicionarCarta(baralho.puxCarta());
                        parou = true;
                        imprimirMaos();
                    }
                    break;
            }
        }
    }

    private void acaoDealer(Scanner sc){
        dealer.getMao().getCartas().get(0).setVirado(false);
        imprimirMaos();
        while (dealer.valorMao() < 17) {
            System.out.println("Dealer puxa mais uma carta... (Pressione Enter para continuar)");
            sc.nextLine();
            sc.nextLine();
            dealer.adicionarCarta(baralho.puxCarta());
            imprimirMaos();
        }
    }

    private int verificaVitoria(){
        int valorJogador = jogador.valorMao();
        int valorDealer = dealer.valorMao();

        // Jogador estourou
        if (valorJogador > 21) {
            return 1; // perdeu
        }

        // Dealer estourou
        if (valorDealer > 21) {
            return 3; // ganhou
        }

        //  Blackjack natural (21 com 2 cartas)
        boolean jogadorBlackjack = (valorJogador == 21 && jogador.getMao().getCartas().size() == 2);
        boolean dealerBlackjack = (valorDealer == 21 && dealer.getMao().getCartas().size() == 2);

        if (jogadorBlackjack && dealerBlackjack) {
            return 2; // empate
        }
        if (jogadorBlackjack) {
            return 3; // ganhou
        }
        if (dealerBlackjack) {
            return 1; // perdeu
        }

        // 4) Comparação de valores
        if (valorJogador > valorDealer) {
            return 3; // ganhou
        }
        if (valorJogador < valorDealer) {
            return 1; // perdeu
    }

    return 2; // empate
    }

    private void Encerramento_E_Pagamento(Scanner sc){
        System.out.println("\n--- Resultado Final ---");
        imprimirMaos();

        int resultado = verificaVitoria();

        switch (resultado) {
            case 1: // perdeu
                System.out.println("Você perdeu!");
                break;

            case 2: // empate
                System.out.println("Empate!");
                jogador.setBanca(jogador.getBancaCentavos() + fichasApostadas);
                break;

            case 3: // ganhou (pode ser normal ou blackjack)
                boolean jogadorBlackjack = (jogador.valorMao() == 21 && jogador.getMao().getCartas().size() == 2);

                if (jogadorBlackjack) {
                    System.out.println("Parabéns! Blackjack!");
                    fichasApostadas = (int) Math.round(fichasApostadas * 2.5);
                } else {
                    System.out.println("Parabéns!! Você ganhou!");
                    fichasApostadas *= 2;
                }

                jogador.setBanca(jogador.getBancaCentavos() + fichasApostadas);
                break;

            default:
                System.out.println("Erro inesperado no resultado.");
                break;
        }

        System.out.println("Sua banca agora é: " + jogador.getBancaReais());
        System.out.println("\nRodada concluída. Pressione Enter para continuar...");
        sc.nextLine();
        sc.nextLine();
    }

    private void imprimirMaos(){
        Control.limparTerminal();
        dealer.ImprimirMao();
        System.out.println();
        jogador.ImprimirMao();
        System.out.println("---------------------------------\n");
    }


    
}

