package CAP2.actFinal;

public class Time extends Thread {
    private final long startSecond = (System.currentTimeMillis() / 1000);
    private long currentTime;

    @Override
    public void run() {
        while (!isInterrupted()) {
            currentTime = (System.currentTimeMillis() / 1000) - startSecond;

            System.out.println("Time (s): " + currentTime);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }
}

