package CAP2.act0201;

public class HiloTAC extends Thread{
    @Override
    public void run() {
        do {
            System.out.println("TAC");
            System.out.println();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }
}
