package Blackjack.Recursos;

public class Carta {
    
    private int numero;
    private String naipe;
    private String numeroM;
    private boolean virado;

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    
    public String getNaipe() {
        return naipe;
    }
    public void setNaipe(String naipe) {
        this.naipe = naipe;
    }
    
   
    public String getNumeroM() {
        return numeroM;
    }
    public void setNumeroM(String numeroM) {
        this.numeroM = numeroM;
    }

    
    public void setVirado(boolean virado) {
        this.virado = virado;
    }
    public boolean isVirado() {
        return virado;
    }

    public void imprimirArteDaCarta(Carta carta) {
        if (carta.isVirado()) {
            System.out.println("----------------");
            System.out.println("| Carta Virada |");
            System.out.println("----------------");
        } else {
            String textoCarta = "| " + carta.getNumeroM() + " de " + carta.getNaipe() + " |";
            String linhaTracos = "-".repeat(textoCarta.length());

            System.out.println(linhaTracos);
            System.out.println(textoCarta);
            System.out.println(linhaTracos);
        }
        System.out.println();
    }
}