package CAP2.actFinal;

import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {
    private int numberTry;
    private List<Integer> hints;
    private volatile boolean skipTurn = false;
    private volatile boolean inPause = true;

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                interrupt();
            }

            while ((inPause || skipTurn) && !isInterrupted()) { // isInterrupted() ES NECESARIO PORQUE CUANDO EL HILO "DESPIERTE" EL WHILE SIGUE EJECUTANDOSE
                waitTurn();
            }

            if (!isInterrupted()) {
                System.out.println("E M P A N A D A S " + getName());
            }
        }

        System.out.println(" EL JUGADOR " + getName() + " HA MUERTO");
    }

    public Player(String name) {
        super(name);
        hints = new ArrayList<>();
    }

    private synchronized void waitTurn() {
        try {
            wait();
        } catch (InterruptedException e) {
            interrupt();
        }
    }

    public synchronized void unlock() {
        notifyAll();
    }

    public void createNumToTry(int max) {
        numberTry = (int) (Math.random() * max);
    }

    public void receiveHint(int hint) {
        hints.add(hint);
    }

    public int getNumberTry() {
        return numberTry;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public boolean isInPause() {
        return inPause;
    }

    public void setInPause(boolean inPause) {
        this.inPause = inPause;
    }
}
