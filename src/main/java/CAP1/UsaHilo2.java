package CAP1;

public class UsaHilo2 {
    public static void main(String[] args) {
        HiloSimple2 hs = new HiloSimple2();
        Thread t = new Thread(hs);
        t.start();
        for (int i = 0; i < 5; i++) {
            System.out.println("Fuera del hilo... " + i);
        }
    }
}
