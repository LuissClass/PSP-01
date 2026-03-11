package CAP2.act0204;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.util.Scanner;

import static java.lang.System.in;


public class SolicitaSuspender {
    private boolean suspender = false;

    public synchronized void esperandoParaReanudar() throws InterruptedException {
        while (suspender) {
            wait(); // SUSPENDER HILO HASTA RECIBIR notify() O notifyAll()
        }
    }

    public synchronized void set(boolean suspender) {
        this.suspender = suspender;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        int cont = 0;

        MyHilo hilo = new MyHilo();
        String text;

        label:
        while (true) {
            if (cont == 1) {
                hilo.start();
                cont++;
            }

            cont++;
            text = scanner.nextLine();

            switch (text) {
                case "S":
                    hilo.suspende();
                    break;
                case "R":
                    hilo.reanuda();
                    break;
                case "*":
                    hilo.setPararHilo(true);
                    break label;
            }
        }

        hilo.setPararHilo(true);
        System.out.println("Contador final: " + hilo.getContador());
    }
}


class MyHilo extends Thread {
    private final SolicitaSuspender suspender = new SolicitaSuspender();
    private int contador = 0;
    private boolean pararHilo = false;

    public void suspende() {
        suspender.set(true);
    }

    public void reanuda() {
        suspender.set(false);

        synchronized (suspender) {
            suspender.notify();
        }
    }

    public void run() {
        while (!pararHilo) {
            contador++;
            System.out.println("Contador: " + contador);

            try {
                sleep(1000);
                suspender.esperandoParaReanudar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("FIN DEL BUCLE");
    }



    public int getContador() {
        return contador;
    }

    public void setPararHilo(boolean pararHilo) {
        this.pararHilo = pararHilo;
    }
}