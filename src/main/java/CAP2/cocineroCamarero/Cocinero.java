package CAP2.cocineroCamarero;

public class Cocinero implements Runnable {
    Ventanilla s;
    String name;

    Cocinero(Ventanilla s, String name) {
        this.s = s;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String comanda;
                if ((comanda = s.tomarComanda(name)) != null) {
                    s.cocinar(name, comanda);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
