package Caca_Niquel;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Auxiliar.Input;
import Perfil.*;
import View.*;
import static Caca_Niquel.Recursos.CoresANSI.*;

import Caca_Niquel.Recursos.CoresANSI;
import Caca_Niquel.Recursos.Simbolo;

public class Caca_Niquel {
    Scanner sc;
    Jogador jogador;
    Random random = new Random();
    private boolean acabou = false;

    // rolos do caça-niquel
    private ArrayList<Simbolo> rolo1;
    private ArrayList<Simbolo> rolo2;
    private ArrayList<Simbolo> rolo3;

    // Constantes do Grid ASCII
    private static final String BORDA_SUPERIOR = BRANCO_NEG + "╔═══════╗" + RESET;
    private static final String SEPARADOR_LINHA = BRANCO_NEG + "╠═══════╣" + RESET;
    private static final String BORDA_INFERIOR = BRANCO_NEG + "╚═══════╝" + RESET;
    private static final String SEPARADOR_LATERAL = BRANCO_NEG + "║" + RESET;
    private static final String ESPAÇO = " ";
    
    // Matriz 3x3 para armazenar os resultados com os símbolos
    private Simbolo[][] resultadosFinais = new Simbolo[3][3];
    private int fichasApostadas = 0;

    public Caca_Niquel(Scanner sc, Jogador jogador){
        this.sc = sc;
        this.jogador = jogador;
        this.rolo1 = montarRoloVirtual();
        this.rolo2 = montarRoloVirtual();
        this.rolo3 = montarRoloVirtual();
    }

    public void inicio() throws InterruptedException {
        while(!acabou){
            acabou = iniciarRodada();
        }
        
        System.out.println("Saindo do Caça-Níquel... (Pressione Enter para continuar):");
        sc.nextLine();
        sc.nextLine();
    }

    private boolean iniciarRodada() throws InterruptedException {
        if(jogador.getBancaReais() == 0){
            return true;
        }

        Control.limparTerminal();
        Control.printCacaNiquel(); 
        
        System.out.println("Valor da banca: " + jogador.getBancaReais());
        System.out.println("Caso deseje parar de jogar, aposte 0.");
        
        fichasApostadas = Input.lerValorEmCentavos(sc, "Digite a quantidade de fichas para apostar: ");
        if(fichasApostadas == 0){
            return true;
        }

        while(fichasApostadas < 0 || fichasApostadas > jogador.getBancaCentavos()){
            fichasApostadas = Input.lerValorEmCentavos(sc, "Aposta inválida. Tente novamente: ");
        }

        jogador.setBanca(jogador.getBancaCentavos() - fichasApostadas);

        // Realiza o sorteio final antes da animação
        realizarSorteio(); 

        // Executa a animação
        System.out.println("\nCaça-Níquel girando... Pressione Enter para começar.");
        sc.nextLine();
        animarGiroSequencial(resultadosFinais);
        
        // Analisa e paga
        double multiplicadorTotal = analisarResultados(resultadosFinais);
        encerramentoEpagamento(fichasApostadas, multiplicadorTotal);
        
        return false;
    }
    private ArrayList<Simbolo> montarRoloVirtual() {
        ArrayList<Simbolo> rolo = new ArrayList<>();
        for (Simbolo s : Simbolo.values()) {
            // Adiciona o símbolo ao rolo o número de vezes do seu peso
            for (int i = 0; i < s.getPesoProbabilidade(); i++) {
                rolo.add(s);
            }
        }
        // Embaralha o rolo
        Collections.shuffle(rolo);
        return rolo;
    }
    
    // Lógica para sortear os símbolos com base no peso de probabilidade
    private void realizarSorteio() {
        // Rolo 1 (Coluna 0)
        int parada1 = random.nextInt(rolo1.size()); // Escolhe aleatóriamente um simbolo do rolo
        resultadosFinais[0][0] = rolo1.get(parada1);
        resultadosFinais[1][0] = rolo1.get((parada1 + 1) % rolo1.size());
        resultadosFinais[2][0] = rolo1.get((parada1 + 2) % rolo1.size());

        // Rolo 2 (Coluna 1)
        int parada2 = random.nextInt(rolo2.size());
        resultadosFinais[0][1] = rolo2.get(parada2);
        resultadosFinais[1][1] = rolo2.get((parada2 + 1) % rolo2.size());
        resultadosFinais[2][1] = rolo2.get((parada2 + 2) % rolo2.size());
        
        // Rolo 3 (Coluna 2)
        int parada3 = random.nextInt(rolo3.size());
        resultadosFinais[0][2] = rolo3.get(parada3);
        resultadosFinais[1][2] = rolo3.get((parada3 + 1) % rolo3.size());
        resultadosFinais[2][2] = rolo3.get((parada3 + 2) % rolo3.size());
    }
    
    // Cria um resultado aleatório para o frame da animação
    private Simbolo[][] gerarFrameAleatorio() {
        Simbolo[] simbolos = Simbolo.values();
        Simbolo[][] frame = new Simbolo[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Sorteio simples (sem peso) 
                frame[i][j] = simbolos[random.nextInt(simbolos.length)];
            }
        }
        // Retorna uma matris 3x3 totalmente aleatória para impressão
        return frame;
    }

    // Controla a animação de giro e as paradas sequenciais.
    private void animarGiroSequencial(Simbolo[][] resultadosFinais) throws InterruptedException {
        // Matriz que controla/armazena o estado atual que vai ser impresso
        Simbolo[][] estadoAtual = new Simbolo[3][3];
        
        // Tempos de parada sequenciais (em milissegundos)
        long inicioAnimacao = System.currentTimeMillis();
        long tempoColuna0Parar = inicioAnimacao + 1500; // Coluna 0 para em 1.5s
        long tempoColuna1Parar = inicioAnimacao + 3000; // Coluna 1 para em 3.0s
        long tempoColuna2Parar = inicioAnimacao + 4500; // Coluna 2 para em 4.5s
        
        boolean coluna0Parada = false;
        boolean coluna1Parada = false;
        boolean coluna2Parada = false;
        
        // Loop principal da animação
        while (!coluna0Parada || !coluna1Parada || !coluna2Parada) {
            
            Simbolo[][] frameAleatorio = gerarFrameAleatorio();
            Control.limparTerminal();
            Control.printCacaNiquel();

            long tempoAtual = System.currentTimeMillis();

            // Lógica para coluna 0 (Esquerda)
            if (!coluna0Parada) {
                if (tempoAtual > tempoColuna0Parar) {
                    // Copia o resultado final
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][0] = resultadosFinais[i][0];
                    }
                    coluna0Parada = true;
                } else {
                    // Coluna 0 continua girando
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][0] = frameAleatorio[i][0];
                    }
                }
            }

            // Lógica para coluna 1 (Meio)
            if (!coluna1Parada) {
                if (tempoAtual > tempoColuna1Parar) {
                    // Copia o resultado final
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][1] = resultadosFinais[i][1];
                    }
                    coluna1Parada = true;
                } else {
                    // Coluna 1 continua girando
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][1] = frameAleatorio[i][1];
                    }
                }
            }

            // Lógica para coluna 2 (Direita)
            if (!coluna2Parada) {
                if (tempoAtual > tempoColuna2Parar) {
                    // Copia o resultado final
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][2] = resultadosFinais[i][2];
                    }
                    coluna2Parada = true;
                } else {
                    // Coluna 2 continua girando
                    for (int i = 0; i < 3; i++) {
                        estadoAtual[i][2] = frameAleatorio[i][2];
                    }
                }
            }
            
            // Imprime o frame atual
            imprimirGrid(estadoAtual); 
            System.out.println("\n--------- CAÇA-NÍQUEL ---------");
            System.out.println("Banca: " + jogador.getBancaReais() + " | Aposta: " + String.format("R$ %.2f", (double)fichasApostadas / 100.0));
            
            Thread.sleep(100); // Pausa para o efeito visual 
        }
        
        Control.limparTerminal();
        Control.printCacaNiquel();
    }
    
    private double analisarResultados(Simbolo[][] resultados) {
        double multiplicadorTotal = 0.0;
        
        System.out.println("\n--- ANÁLISE DE LINHAS VENCEDORAS ---");
        
        // Linhas horizontais
        for (int i = 0; i < 3; i++) {
            if (resultados[i][0] == resultados[i][1] && resultados[i][1] == resultados[i][2]) {
                multiplicadorTotal += resultados[i][0].getMultiplicador();
                System.out.printf("Ganho na Linha %d! Multiplicador: %.1fx (%s)\n", 
                                  (i + 1), resultados[i][0].getMultiplicador(), 
                                  resultados[i][0].getRepresentacao().trim());
            }
        }

        // Linhas verticais
        for (int j = 0; j < 3; j++) {
            if (resultados[0][j] == resultados[1][j] && resultados[1][j] == resultados[2][j]) {
                multiplicadorTotal += resultados[0][j].getMultiplicador();
                System.out.printf("Ganho na Coluna %d! Multiplicador: %.1fx (%s)\n", 
                                (j + 1), resultados[0][j].getMultiplicador(), 
                                resultados[0][j].getRepresentacao().trim());
            }
        }

        // Diagonal principal 
        if (resultados[0][0] == resultados[1][1] && resultados[1][1] == resultados[2][2]) {
            multiplicadorTotal += resultados[0][0].getMultiplicador();
            System.out.printf("Ganho na Diagonal Principal! Multiplicador: %.1fx (%s)\n", 
                                resultados[0][0].getMultiplicador(), 
                                resultados[0][0].getRepresentacao().trim());
        }

        // Diagonal secundária
        if (resultados[0][2] == resultados[1][1] && resultados[1][1] == resultados[2][0]) {
            multiplicadorTotal += resultados[0][2].getMultiplicador();
            System.out.printf("Ganho na Diagonal Secundária! Multiplicador: %.1fx (%s)\n", 
                                resultados[0][2].getMultiplicador(), 
                                resultados[0][2].getRepresentacao().trim());
        }

        return multiplicadorTotal;
    }

    private void encerramentoEpagamento(int fichasApostadas, double multiplicadorTotal) {
        
        System.out.println("\n--- RESULTADO FINAL ---");
        imprimirGrid(resultadosFinais); // Imprime o grid final

        if (multiplicadorTotal > 0) {
            // O pagamento é aposta * multiplicador. 
            long pagamentoTotalCentavos = Math.round(fichasApostadas * multiplicadorTotal); 
            
            jogador.setBanca(jogador.getBancaCentavos() + (int)pagamentoTotalCentavos);

            System.out.printf("\nPARABÉNS! Você ganhou um total de %.1fx sua aposta!\n", multiplicadorTotal);
            System.out.printf("Pagamento total: R$ %.2f\n", pagamentoTotalCentavos / 100.0);
        } else {
            System.out.println("\nVocê não teve combinações vencedoras. Tente novamente!");
        }

        System.out.println("Sua banca agora é: " + jogador.getBancaReais());
        System.out.println("\nRodada concluída. Pressione Enter para continuar...");
        sc.nextLine();
    }


    // Método de impressão do Grid
    private void imprimirGrid(Simbolo[][] resultados) {
        
        // Linha 1: Borda Superior
        System.out.println(BORDA_SUPERIOR + BORDA_SUPERIOR + BORDA_SUPERIOR);

        // Linha 2: Símbolos da Linha 0
        imprimirLinhaDeSimbolos(resultados[0]);

        // Linha 3: Separador interno
        System.out.println(SEPARADOR_LINHA + SEPARADOR_LINHA + SEPARADOR_LINHA);

        // Linha 4: Símbolos da Linha 1 (Meio)
        imprimirLinhaDeSimbolos(resultados[1]);

        // Linha 5: Separador interno
        System.out.println(SEPARADOR_LINHA + SEPARADOR_LINHA + SEPARADOR_LINHA);

        // Linha 6: Símbolos da Linha 2
        imprimirLinhaDeSimbolos(resultados[2]);

        // Linha 7: Borda inferior
        System.out.println(BORDA_INFERIOR + BORDA_INFERIOR + BORDA_INFERIOR);
    }

    private void imprimirLinhaDeSimbolos(Simbolo[] linha) {
        final String SEPARADOR_COLUNA = CoresANSI.BRANCO_NEG + "  ║║" + CoresANSI.RESET;

        // Coluna 1
        System.out.print(SEPARADOR_LATERAL + ESPAÇO); 
        System.out.print(linha[0].getSimboloColorido());
        
        // Separador entre coluna 1 e 2
        System.out.print(ESPAÇO + SEPARADOR_COLUNA + ESPAÇO); 
        System.out.print(linha[1].getSimboloColorido());
        
        // Separador entre coluna 2 e 3
        System.out.print(ESPAÇO + SEPARADOR_COLUNA + ESPAÇO);
        System.out.print(linha[2].getSimboloColorido());
        
        // última coluna
        System.out.println(ESPAÇO + "  " + SEPARADOR_LATERAL);
    }
}
