package CAP2.cocineroCamarero;

import java.util.ArrayList;
import java.util.List;

public class Ventanilla {
    public List<String> platos = new ArrayList<>();
    public List<String> comandas = new ArrayList<>();
    public volatile int cont = 0;
    private final static int PLATOS_VENTANILLA = 3;
    private final static int COMANDAS_VENTANILLA = 3;

    public synchronized void llevarPlato(String name) throws InterruptedException {
        while (platos.isEmpty()) {
            System.out.println("CAMARERO " + name + " NO TIENE PLATOS QUE RECOGER");
            wait();
        }

        notifyAll();
        System.out.println("Plato recogido por camarero " + name + ": " + platos.removeLast());
    }

    public synchronized void ponerComanda(String nameCamarero) throws InterruptedException {
        while (comandas.size() >= COMANDAS_VENTANILLA) {
            wait();
        }

        String comanda = "Comanda #" + cont;
        System.out.println("Camarero " + nameCamarero + " pone la comanda " + comanda);
        comandas.add(comanda);
    }

    public synchronized void cocinar(String name, String comanda) throws InterruptedException {
        while (platos.size() >= PLATOS_VENTANILLA) {
            System.out.println("COCINERO " + name + " LLENÓ LA VENTANILLA ");
            wait();
        }

        platos.add(comanda);
        System.out.println("Cocinero cocinó comanda " + comanda);
        cont++;

        notifyAll();
    }

    public synchronized String tomarComanda(String nameCocinero) throws InterruptedException {
        while (comandas.isEmpty()) {
            wait();
        }

        System.out.println("Cocinero " + nameCocinero + " toma la comanda " + comandas.getLast());
        return comandas.removeLast();
    }
}
