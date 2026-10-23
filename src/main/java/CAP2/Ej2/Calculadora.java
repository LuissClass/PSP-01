package CAP2.Ej2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        boolean correcto = false;
        int a = 0;
        int b = 0;

        while (!correcto) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.println("NUMERO a: ");
                a = input.nextInt();
                System.out.println("NUMERO b: ");
                b = input.nextInt();

                correcto = true;
            } catch (InputMismatchException e) {
                System.out.println("Formato incorrecto");
            }
        }

        Suma s = new Suma(a, b);
        s.run();
        Resta r = new Resta(a, b);
        r.run();
    }
}

class Suma implements Runnable{
    int a;
    int b;

    public Suma(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        System.out.println("Suma: " + (a+b));
    }
}

class Resta implements Runnable{
    int a;
    int b;

    public Resta(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        System.out.println("Resta: " + (a-b));
    }
}