package View;

public class Control {
    public static void limparTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printCassino() {
                    System.out.println("""
            +==================================================+
            ||                                                  ||
            ||              BEM-VINDO AO CASSINO                ||
            ||                                                  ||
            +==================================================+
                    """);
                }

    public static void printBlackjack() {
                    System.out.println("""
            +==================================================+
            ||                                                  ||
            ||             BEM-VINDO AO BLACKJACK               ||
            ||                                                  ||
            +==================================================+
                    """);
                }

    public static void printBackBo() {
                System.out.println("""
        +==================================================+
        ||                                                  ||
        ||              BEM-VINDO AO BACK-BO                ||
        ||                                                  ||
        +==================================================+
                """);
        }
}
