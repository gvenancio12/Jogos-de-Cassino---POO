package Perfil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Banca {
    private int banca;

    public Banca(Scanner sc) {
        this.banca = validate(sc);
    }

    private int validate(Scanner sc) {
        while (true) {
            try {
                System.out.print("Digite o valor inicial da banca (maior que 0): ");
                int valor = sc.nextInt();

                if (valor > 0) {
                    return valor * 100;
                } else {
                    System.out.println("O valor deve ser maior que 0. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                sc.next();
            }
        }
    }

    public void multiplicar(double multiplicador) {
        banca = (int) Math.round(banca * multiplicador);
    }

    // tem que passar os valores em centavos, ou seja *100
    public void setBanca(int valor){
        this.banca = valor;
    }
    
    public int getBanca() {
        return banca;
    }

    
}
