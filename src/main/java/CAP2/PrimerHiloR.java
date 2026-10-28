package CAP2;

public class PrimerHiloR implements Runnable{
    @Override
    public void run() {
        System.out.println("Hola gente! " + Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        new Thread(new PrimerHiloR()).start();
        new Thread(new PrimerHiloR()).start();
        new Thread(new PrimerHiloR()).start();
    }
}
