package CAP2.cocineroCamarero;

public class Camarero implements Runnable{
    Ventanilla s;
    String name;

    Camarero(Ventanilla s, String name) {
        this.s = s;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                s.ponerComanda(name);
                s.llevarPlato(name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}
