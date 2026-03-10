package CAP2.act0201;

public class HiloTIC extends Thread {
    @Override
    public void run() {
        do {
            System.out.println("TIC");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (true);

    }
}
