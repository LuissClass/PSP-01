package CAP2;

import java.util.Scanner;

public class MyHilo01 extends Thread {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe algo cuando quieras...");
        String texto = sc.nextLine();
        System.out.println("Has escrito: " + texto);
    }
}

class HiloReloj extends Thread {
    @Override
    public void run() {
        int segundos = 0;
        while (true) {
            System.out.println("Han pasado " + segundos + " segundos");
             segundos++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}

class Main01 {
    public static void main(String[] args) {
        new MyHilo01().start();
        new HiloReloj().start();
    }
}
