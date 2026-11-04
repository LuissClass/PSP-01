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
            try {
                s.consumir(name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}
