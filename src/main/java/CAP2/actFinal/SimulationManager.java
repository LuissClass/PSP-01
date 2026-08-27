package CAP2.actFinal;

// SE ENCARGA DE INICIAR Y TERMINAS LAS PARTIDAS

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SimulationManager {

    // SE PUEDEN CAMBIAR DEPENDIENDO DE LA PARTIDA
    private static final int MAX_NUMBER = 1000;
    private static final int MAX_TIME_SECONDS = 5;

    private Player winner;
    private final CountDownLatch gameFinished = new CountDownLatch(1);

    public void run() {
        List<Player> players = createPlayers();

        Referee r = new Referee(MAX_NUMBER, players, this);

        // CREATE THE ScheduledExecutorService WITH A "pool" OF 1 THREAD
        ScheduledExecutorService timerScheduler = Executors.newScheduledThreadPool(1);

        ExecutorService refereeExecutor = Executors.newSingleThreadExecutor();

        ExecutorService playersExecutor = Executors.newFixedThreadPool(players.size());

        executeExecutors(r, players, refereeExecutor, playersExecutor, timerScheduler);

        try {
            gameFinished.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        stopByWinner(refereeExecutor, playersExecutor, timerScheduler);
    }


    private void shutdownExecutors(
            ExecutorService refereeExecutor,
            ExecutorService playersExecutor,
            ScheduledExecutorService scheduler
    ) {
        refereeExecutor.shutdownNow();
        playersExecutor.shutdownNow();
        scheduler.shutdownNow();
    }

    private List<Player> createPlayers() {
        return new ArrayList<>(List.of(
                new Player("Player 1", MAX_NUMBER),
                new Player("Player 2", MAX_NUMBER),
                new Player("Player 3", MAX_NUMBER)
        ));
    }

    private void stopByTimeout(
            Referee referee,
            ExecutorService refereeExecutor,
            ExecutorService playersExecutor,
            ScheduledExecutorService timerScheduler) {
        referee.stopGame();
        System.out.println("TIME IS OVER (" + MAX_TIME_SECONDS + "s)");

        shutdownExecutors(refereeExecutor, playersExecutor, timerScheduler);

        gameFinished.countDown();
        System.out.println("🛑 >> THE SIMULATION HAS ENDED - BYE BYE");
    }

    private void stopByWinner(
            ExecutorService refereeExecutor,
            ExecutorService playersExecutor,
            ScheduledExecutorService timerScheduler
    ) {
        if (winner == null) {
            return;
        }

        System.out.println("\n🎯 THERE IS A WINNER: " + winner.getName());

        shutdownExecutors(refereeExecutor, playersExecutor, timerScheduler);

        System.out.println("🛑 >> A WINNER STOPPED THE SIMULATION");
    }

    private void executeExecutors(
            Referee referee,
            List<Player> players,
            ExecutorService refereeExecutor,
            ExecutorService playersExecutor,
            ScheduledExecutorService timerScheduler
    ) {
        try {
            refereeExecutor.execute(referee::runReferee);
            for (Player player : players) {
                playersExecutor.execute(player::run);
            }
            timerScheduler.schedule(() -> stopByTimeout(referee, refereeExecutor, playersExecutor, timerScheduler), MAX_TIME_SECONDS, TimeUnit.SECONDS);
        } catch (RejectedExecutionException e) {
            shutdownExecutors(refereeExecutor, playersExecutor, timerScheduler);
        }
    }


    public void setWinner(Player winner) {
        this.winner = winner;
        gameFinished.countDown();
    }
}










