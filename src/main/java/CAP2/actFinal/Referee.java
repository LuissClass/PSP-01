package CAP2.actFinal;

import java.util.List;

public class Referee extends Thread {
    private int secretNumber; // NUMERO A ADIVINAR
    private List<Player> players;
    private int round; // NUMERO DE RONDA
    private RoundState roundState; // ESTADO DE LA RONDA
    private Player blockedPlayer;

    Referee(int maxNum, List<Player> players) {
        secretNumber = (int) (Math.random() * maxNum); // EL NUMERO SECRETO SIEMPRE ES POSITIVO
        this.players = players;
        round = 0;
        roundState = RoundState.NOT_STARTED;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (roundState == RoundState.NOT_STARTED) {

                allPlayersWait();
                //iniciarRonda();
                System.out.println("EL Referee HA BLOQUEADO A TODOS LOS Player");

                try {
                    sleep(2500);
                    unlockPlayers();
                    System.out.println("EL Referee HA DESBLOQUEADO A TODOS LOS Player");
                    sleep(2500);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }

        System.out.println("EL ARBITRO HA MUERTO");
    }

    private void allPlayersWait() {
        for (Player player : players) {
            player.setInPause(true);
        }
    }

    private void unlockPlayers() {
        for (Player player : players) {
            if (!player.isSkipTurn()) {
                player.setInPause(false);
                player.unlock();
            }
        }
    }

    private synchronized void skipTurn() {
        if (blockedPlayer != null) {
            blockedPlayer.setSkipTurn(true);
        }
    }

    private synchronized void giveBackTurn() {
        if (blockedPlayer != null) {
            blockedPlayer.setSkipTurn(false);
            blockedPlayer = null;
        }
    }

    private void iniciarRonda() {
        roundState = RoundState.IN_PROGRESS;
        round++; // ACTUALIZAR NUMERO DE RONDA

        System.out.println("\n\n=== RONDA " + round + " ===\n");

        for (Player p : players) { // CREA LA JUGADA DE TODOS LOS PLAYERS
            p.createNumToTry(secretNumber);
        }

        giveBackTurn(); // DAR EL TURNO AL JUGADOR QUE ESTABA BLOQUEADO
        analizarTurnos(); // CAMBIA EL VALOR DE blockedPlayer
        skipTurn(); // QUITAR EL TURNO AL JUGADO R MÁS CERCA DE ADIVINAR EL NÚMERO SECRETO

        roundState = RoundState.NOT_STARTED;
    }

    private void analizarTurnos() {
        int maxValue = -1;
        int posPlayer = -1;
        int actualPlayerValue = -1;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isSkipTurn()) { // SI EL PLAYER ESTA BLOQUEADO SE LO SALTA
                System.out.println("EL JUGADOR " + players.get(i).getName() + " NO HA PODIDO JUGAR ESTA RONDA :C");
                continue;
            }

            actualPlayerValue = players.get(i).getNumberTry();

            System.out.println("EL JUGADOR " + players.get(i).getName() + " HA DICHO EL NÚMERO: " + actualPlayerValue);
            verifyNumber(actualPlayerValue);

            if (actualPlayerValue > maxValue) {
                maxValue = actualPlayerValue;
                posPlayer = i;
            }
        }

        blockedPlayer = blockedPlayer == null ? players.get(posPlayer) : null;
        giveHintToPlayers(maxValue); // DAR LA PISTA AL RESTO DE JUGADORES
    }

    private void giveHintToPlayers(int hint) {
        for (Player p : players) {
            p.receiveHint(hint);
        }
    }

    public void verifyNumber(int num) {
        if (num == secretNumber) {
            System.out.println("¡Y ES CORRECTO!");
            System.out.println("¡HA GANADO!");
            stopGame();
        } else {
            System.out.println("PERO HA FALLADO :=C");
        }
    }

    public void stopGame() {
        for (Player p : players) { // PRIMERO MANDAMOS SEÑAL DE INTERRUPCIÓN A TODOS LOS HILOS Player
            p.interrupt();
        }

        roundState = RoundState.FINISHED_GAME;
        interrupt(); // AHORA AL HILO Referee
    }
}
