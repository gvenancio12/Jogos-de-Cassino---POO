
import java.util.Scanner;

import Blackjack.*;
import Perfil.*;
import Auxiliar.*;
import Bac_Bo.Bac_Bo;
import View.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        Control.printCassino();
        Jogador jogador = new Jogador(sc);

        int escolha = 0;
        while(escolha != 4){
           
            Control.limparTerminal();
            // Se o jogador não tiver dinheiro para jogar o jogo encerra automaticamente
            if(jogador.getBancaReais()==0){
                break;
            }

            Control.printCassino();
            System.out.println(jogador.getNome() + " sua banca é de: " + jogador.getBancaReais());
            System.out.println("1 - Blackjack");
            System.out.println("2 - Caça-Níquel");
            System.out.println("3 - Bac-Bo");
            System.out.println("4 - Ir embora");
            escolha = Input.lerInt(sc, "Escolha sua opção: ", 1, 4);

            switch (escolha) {
                case 1:
                    Blackjack blackjack = new Blackjack(sc, jogador);
                    blackjack.iniciar(sc);
                    break;
                case 2:
                    System.out.println("Implementar Caça-Níquel.");
                    break;
                case 3:
                    Bac_Bo bac_Bo = new Bac_Bo(sc, jogador);
                    bac_Bo.iniciar();
                    break;
                case 4:
                    System.out.println("Saindo do casino...");
                    System.out.println("Obrigado por jogar!");
                    break;
            }
        
        }
        if(jogador.getBancaReais() == 0){
            System.out.println("Sobrou nada...");
        }
        sc.close();
    }
  
}

