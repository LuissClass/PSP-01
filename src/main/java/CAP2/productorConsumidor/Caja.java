package CAP2.productorConsumidor;

import java.util.ArrayList;
import java.util.List;

public class Caja {
    public List<String> manzanas = new ArrayList<>();
    public StringBuilder boxView = new StringBuilder("");
    public volatile int cont = 0;

    public synchronized void consumir(String name) throws InterruptedException {
        while (manzanas.isEmpty()) {
            System.out.println("CONSUMIDOR " + name + " NO TIENE MANZANAS");
            wait();
        }

        notifyAll();
        System.out.println("Manzana 🍎 consumida por consumidor " + name + ": " + manzanas.removeLast());

        boxView.deleteCharAt(boxView.length() - 1);
        boxView.deleteCharAt(boxView.length() - 1);
        System.out.println("(" + manzanas.size() +") " + boxView);
    }

    public synchronized void producir(String name) throws InterruptedException {
        while (manzanas.size() >= 5) {
            System.out.println("PRODUCTOR " + name + " TIENE LA CAJA LLENA");
            wait();
        }

        manzanas.add("Manzana " + cont);
        System.out.println("Manzana " + cont + " producida por productor " + name);
        cont++;

        boxView.append("🍎");

        System.out.println("(" + manzanas.size() +") " + boxView);
        notifyAll();
    }

}
