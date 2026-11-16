package CAP2.cocineroCamarero;

public class App {
    public static void main(String[] args) {
        Ventanilla s = new Ventanilla();

        new Thread(new Cocinero(s, "1")).start();
        new Thread(new Cocinero(s, "2")).start();
        new Thread(new Cocinero(s, "3")).start();
        new Thread(new Camarero(s, "1")).start();
        new Thread(new Camarero(s, "2")).start();
        new Thread(new Camarero(s, "3")).start();
    }
}
