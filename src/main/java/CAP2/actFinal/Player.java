package CAP2.actFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import static java.lang.Thread.sleep;

public class Player {
    private final List<Integer> hints;
    private volatile boolean skipTurn = false;
    private final String name;
    private volatile boolean isGamesActive;
    private final int maxNum;
    private boolean numExist;
    private final BlockingQueue<Integer> numberTry = new SynchronousQueue<>();


    public void run() {
        while (isGamesActive) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                isGamesActive = false;
            }

            while ((skipTurn) && isGamesActive) {
                waitTurn();
            }
            //&& !numExist
            if (isGamesActive) { // TODO QUITAR TANTO isGamesActive
                createNumToTry();
                numExist = true;
            }
        }

        //System.out.println(" EL JUGADOR " + getName() + " HA MUERTO");
    }

    public Player(String name, int maxNum) {
        this.name = name;
        this.maxNum = maxNum;
        hints = new ArrayList<>();
        isGamesActive = true;
    }

    private synchronized void waitTurn() {
        try {
            numExist = false;
            wait();
        } catch (InterruptedException e) {
            isGamesActive = false;
        }
    }


    public synchronized void unlockTurn() {
        notify(); // "DESPIERTA" A UN HILOS CON EL MONITOR DE LA CLASE Player (COMO SOLO HAY UNO "DORMIDO", DESPIERTA A ESE)
    }

    public void createNumToTry() { // TODO HAY QUE USAR LAS "PISTAS"
        try {
            int n = (int) (Math.random() * maxNum);
            numberTry.put(n);
        } catch (InterruptedException e) {
            System.out.println("💀 ERROR AL CREAR numberTry");
        }
    }

    public void receiveHint(int hint) {
        hints.add(hint);
    }


    public String getName() {
        return name;
    }

    public BlockingQueue<Integer> getNumberTry() {
        return numberTry;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public void setGamesActive(boolean gamesActive) {
        isGamesActive = gamesActive;
    }

    public boolean isNumExist() {
        return numExist;
    }

    public void setNumExist(boolean numExist) {
        this.numExist = numExist;
    }
}
