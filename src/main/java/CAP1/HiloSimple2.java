package CAP1;

public class HiloSimple2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("En el hilo... ");
        }
    }
}
