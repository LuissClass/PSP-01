package CAP2.cocineroCamarero;

import java.util.ArrayList;
import java.util.List;

public class Ventanilla {
    public volatile List<String> platos = new ArrayList<>();
    public volatile List<String> comandas = new ArrayList<>();
    public volatile int cont = 0;
    private final static int PLATOS_VENTANILLA = 3;
    private final static int COMANDAS_VENTANILLA = 3;

    public synchronized void llevarPlato(String camareroName) throws InterruptedException {
        while (platos.isEmpty()) {
            wait();
        }

        System.out.println("Plato recogido por camarero " + camareroName + ": " + platos.removeLast());
        notifyAll();
    }

    public synchronized void ponerComanda(String nameCamarero) throws InterruptedException {
        while (comandas.size() >= COMANDAS_VENTANILLA) {
            wait();
        }


        String comanda = "Comanda #" + cont;
        cont++;
        System.out.println("Camarero " + nameCamarero + " pone la comanda " + comanda);
        comandas.add(comanda);
        notifyAll();
    }

    public synchronized void cocinar(String nameCocinero, String comanda) throws InterruptedException {
        while (platos.size() >= PLATOS_VENTANILLA) {
            wait();
        }


        platos.add(comanda);
        System.out.println("Cocinero " + nameCocinero + " cocinó comanda " + comanda);

        notifyAll();
    }

    public synchronized String tomarComanda(String nameCocinero) throws InterruptedException {
        while (comandas.isEmpty()) {
            wait();
        }

        String comanda = comandas.removeLast();
        System.out.println("Cocinero " + nameCocinero + " toma la comanda " + comanda);
        notifyAll();
        return comanda;
    }
}
