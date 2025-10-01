package Perfil;

import java.util.Scanner;

import Auxiliar.Input;
import Blackjack.Recursos.*;

public class Jogador extends Pessoa {
    private Banca banca;

    public Jogador(Scanner sc) {
        super(Input.lerString(sc, "Digite seu nome: "));
        this.banca = new Banca(sc);
    }

    // retorna em centavos
    public int getBancaCentavos() {
        return this.banca.getBanca();
    }

    public int getBancaReais(){
        return this.banca.getBanca()/100;
    }

    // tem que passar os valores em centavos, ou seja *100
    public void setBanca(int valor) {
        banca.setBanca(valor);
    }


    @Override
    public void ImprimirMao() {
        System.out.println("Mão de " + getNome() + ": ");
        for(Carta carta : getMao().getCartas()) {
            carta.imprimirArteDaCarta(carta);
        }
        System.out.println("Valor da mão: " + getMao().calculaValor());
    }
}