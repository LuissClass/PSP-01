package CAP2.cocineroCamarero;

public class App {
    public static void main(String[] args) {
        Ventanilla s = new Ventanilla();

        new Thread(new Cocinero(s, "🧑‍🍳")).start();
        new Thread(new Cocinero(s, "🧑‍🍳")).start();
        new Thread(new Cocinero(s, "🧑‍🍳")).start();
        new Thread(new Camarero(s, "🍽️")).start();
        new Thread(new Camarero(s, "🍽️")).start();
        new Thread(new Camarero(s, "🍽️")).start();
    }
}
