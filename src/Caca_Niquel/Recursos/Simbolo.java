package Caca_Niquel.Recursos;
import static Caca_Niquel.Recursos.CoresANSI.*;

// Enum para representar os símbolos do caça-níquel, suas cores, multiplicadores e pesos
public enum Simbolo {
    // Peso: 7=1, #=2, @=3, $=3, &=4, ?=5, 0=7. Total de pesos = 25.

    SETE(" 7 ", 25.0, 1, AMARELO),      // Payout 25.0x | 4% chance
    CERQUILHA(" # ", 15.0, 2, VERDE),    // Payout 15.0x | 8% chance
    ARROBA(" @ ", 10.0, 3, CIANO),       // Payout 10.0x | 12% chance
    DOLAR(" $ ", 7.5, 3, VERDE),        // Payout 7.5x  | 12% chance
    ECOMERCIAL(" & ", 5.0, 4, MAGENTA), // Payout 5.0x  | 16% chance
    INTERROGACAO(" ? ", 3.0, 5, VERMELHO), // Payout 3.0x  | 20% chance
    ZERO(" 0 ", 1.5, 7, CIANO);          // Payout 1.5x  | 28% chance (Mais comum)

    private final String representacao;
    private final double multiplicador; 
    private final int pesoProbabilidade; 
    private final String corANSI;

    Simbolo(String representacao, double multiplicador, int pesoProbabilidade, String corANSI) {
        this.representacao = representacao;
        this.multiplicador = multiplicador;
        this.pesoProbabilidade = pesoProbabilidade;
        this.corANSI = corANSI;
    }

    public String getRepresentacao() {
        return representacao;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public int getPesoProbabilidade() {
        return pesoProbabilidade;
    }

    // Retorna a representação do símbolo formatada com a cor
    public String getSimboloColorido() {
        return this.corANSI + this.representacao + RESET;
    }

    public static int getTotalPesos() {
        int total = 0;
        for (Simbolo simbolo : Simbolo.values()) {
            total += simbolo.pesoProbabilidade;
        }
        return total;
    }

}