package Blackjack.Recursos;

import java.util.ArrayList;

public class Mao {
    private ArrayList<Carta> cartas;
    
    public Mao() {
        cartas = new ArrayList<>();
    }

    public void addCarta(Carta carta) {
        cartas.add(carta);
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void resetMao() {
        cartas.clear();
    }
    
    public int calculaValor() {
        int valor = 0;
        int ases = 0;
        for (Carta carta : cartas) {
            if(carta.isVirado()){
                continue;
            }
            if (carta.getNumero() == 1) {
                ases++;
                valor += 11;
            } else if (carta.getNumero() >= 10) {
                valor += 10;
            } else {
                valor += carta.getNumero();
            }
        }
        while (valor > 21 && ases > 0) {
            valor -= 10;
            ases--;
        }
        return valor;
    }
}
