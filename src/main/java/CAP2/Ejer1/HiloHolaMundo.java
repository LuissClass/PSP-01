package CAP2.Ejer1;

public class HiloHolaMundo extends Thread{
    @Override
    public void run() {
        System.out.println("Hola Mundo! " + Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        HiloHolaMundo h;

        for (int i = 0; i < 5; i++) {

            h = new HiloHolaMundo();
            h.start();
        }
    }
}
