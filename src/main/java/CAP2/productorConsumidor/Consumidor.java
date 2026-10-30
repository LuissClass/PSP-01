package CAP2.productorConsumidor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Consumidor implements Runnable{
    Caja s;
    String name;

    Consumidor(Caja s, String name) {
        this.s = s;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            s.consumir(name);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}
