package Blackjack.Recursos;
import java.util.ArrayList;
import java.util.Collections;

public class Baralho {
    private ArrayList<Carta> baralho;

    public Baralho() {
        this.baralho = new ArrayList<Carta>();
        montarEBaralhar();
    }

    public void resetBaralho() {
        this.baralho.clear();
        montarEBaralhar();
    }
    
    private void montarEBaralhar() {
        for(int i=0; i<4; i++) {
            for(int j=1; j<=13; j++) {
                Carta carta = new Carta();
                switch(j) {
                    case 1:
                        carta.setNumeroM("A");
                        break;
                    case 11:
                        carta.setNumeroM("J");
                        break;
                    case 12:
                        carta.setNumeroM("Q");
                        break;
                    case 13:
                        carta.setNumeroM("K");
                        break;
                    default:
                        carta.setNumeroM(String.valueOf(j));
                }
                carta.setNumero(j);
                switch(i) {
                    case 0:
                        carta.setNaipe("Copas");
                        break;
                    case 1:
                        carta.setNaipe("Ouros");
                        break;
                    case 2:
                        carta.setNaipe("Espadas");
                        break;
                    case 3:
                        carta.setNaipe("Paus");
                        break;
                }
                this.baralho.add(carta);
            }
        }
        Collections.shuffle(this.baralho);
    }

    public Carta puxCarta() {
        if(this.baralho.isEmpty()) {
            resetBaralho();
        }
        return this.baralho.remove(0);
    }
}
