package CAP2.productorConsumidor;

public class Productor implements Runnable {
    Caja s;
    String name;

    Productor(Caja s, String name) {
        this.s = s;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            s.producir(name);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
