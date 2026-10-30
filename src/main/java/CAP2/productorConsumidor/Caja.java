package CAP2.productorConsumidor;

import java.util.ArrayList;
import java.util.List;

public class Caja {
    public List<String> manzanas = new ArrayList<>();
    public volatile int cont = 0;

    public synchronized void consumir(String name) {
        while (manzanas.isEmpty()) {
            try {
                System.out.println("CONSUMIDOR " + name + " NO TIENE MANZANAS");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Manzana 🍎 consumida por consumidor " + name + ": " + manzanas.removeLast());
    }

    public synchronized void producir(String name) {
        if (manzanas.size() <= 4) {
            manzanas.add("Manzana " + cont++);
        } else {
            System.out.println("PRODUCTOR" + name + " LLENO LA CAJA");
        }

        System.out.println("Manzana producida por productor " + name);

        notifyAll();
    }

}
