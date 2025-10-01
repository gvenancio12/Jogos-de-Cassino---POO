package Auxiliar;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    public static int lerInt(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                sc.next();
            }
        }
    }

    public static int lerInt(Scanner sc, String mensagem, int min, int max) {
        while (true) {
            int valor = lerInt(sc, mensagem);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("Digite um valor entre " + min + " e " + max + ".");
        }
    }

    public static String lerString(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine().trim();

            if (!entrada.isEmpty()) {
                return entrada;
            }

            System.out.println("Entrada inválida! O valor não pode ser vazio.");
        }
    }

    public static int lerValorEmCentavos(Scanner sc, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = sc.nextDouble();

                if (valor >= 0) {
                    return (int) Math.round(valor * 100);
                } else {
                    System.out.println("O valor deve ser maior que 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número (ex: 10 ou 10.5).");
                sc.next();
            }
        }
    }

}
