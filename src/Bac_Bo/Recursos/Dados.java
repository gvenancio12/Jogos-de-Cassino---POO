package Bac_Bo.Recursos;

public class Dados {
    private static final String[] frames = {
        """
           +-------+
          /       /|
         +-------+ |
         | o   o | +
         |       |/
         +-------+
        """,
        """
           +-------+
          /       /|
         +-------+ |
         |   o   | +
         | o   o |/
         +-------+
        """,
        """
             +-------+
            /   o   /|
           +-------+ |
           | o   o | +
           |       |/
           +-------+
        """,
        """
           +-------+
          /       /|
         +-------+ |
         | o     | +
         |   o   |/
         +-------+
        """,
        """
           +-------+
          /       /|
         +-------+ |
         | o   o | +
         | o   o |/
         +-------+
        """
    };

    private static final String[] faces = {
        """
        +-------+
        |       |
        |   o   |
        |       |
        +-------+
        """,
        """
        +-------+
        | o     |
        |       |
        |     o |
        +-------+
        """,
        """
        +-------+
        | o     |
        |   o   |
        |     o |
        +-------+
        """,
        """
        +-------+
        | o   o |
        |       |
        | o   o |
        +-------+
        """,
        """
        +-------+
        | o   o |
        |   o   |
        | o   o |
        +-------+
        """,
        """
        +-------+
        | o   o |
        | o   o |
        | o   o |
        +-------+
        """
    };

    // Garante que todas as linhas tenham a mesma largura
    private static String formata(String s, int tamanho) {
        return String.format("%-" + tamanho + "s", s);
    }

    // Imprime dois dados lado a lado, alinhados
    private static void printSideBySide(String esquerda, String direita, int tamanho) {
        String[] l1 = esquerda.split("\n");
        String[] l2 = direita.split("\n");
        int linhas = Math.max(l1.length, l2.length);

        for (int i = 0; i < linhas; i++) {
            String a = (i < l1.length ? formata(l1[i], tamanho) : " ".repeat(tamanho));
            String b = (i < l2.length ? l2[i] : "");
            System.out.println(a + "    " + b);
        }
    }

    public static int getFramesLength() {
        return frames.length;
    }

    public static void imprimirAnimacao(int frame1, int frame2, int tamanho) {
        printSideBySide(frames[frame1], frames[frame2], tamanho);
    }

    public static void imprimirResultado(int jogador, int dealer, int tamanho) {
        printSideBySide(faces[jogador - 1], faces[dealer - 1], tamanho);
    }
}