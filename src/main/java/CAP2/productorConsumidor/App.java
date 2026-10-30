package CAP2.productorConsumidor;

public class App {
    public static void main(String[] args) {
        Caja s = new Caja();

        new Thread(new Consumidor(s, "🧟‍♀️")).start();
        new Thread(new Consumidor(s, "🧟")).start();
        new Thread(new Productor(s, "🧑‍🏭")).start();
        new Thread(new Productor(s, "👷‍♂️")).start();
    }
}
