package Blackjack.Recursos;

import Perfil.Pessoa;

public class Dealer extends Pessoa{
    
    public Dealer(String nome) {
        super(nome);
    }

    @Override
    public void ImprimirMao() {
        System.out.println("Mão do Dealer: ");
        for(Carta carta : mao.getCartas()) {
            carta.imprimirArteDaCarta(carta);
        }
        System.out.println("Valor da mão: " + getMao().calculaValor());
    }

}