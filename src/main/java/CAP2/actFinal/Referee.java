package CAP2.actFinal;

import java.util.List;

import static java.lang.Thread.sleep;

// SE ENCARGA DE GESTIONAR LAS RONDAS Y PARTE DE LA LÓGICA DEL JUEGO

public class Referee {
    private final int secretNumber; // NUMERO A ADIVINAR
    private final List<Player> players;
    private int round; // NUMERO DE RONDA
    private RoundState roundState; // ESTADO DE LA RONDA
    private Player blockedPlayer;
    private volatile boolean isGamesActive;
    private final int maxNum;
    private final SimulationManager simulationManager;
    private int posWinnerPlayer;


    Referee(int maxNum, List<Player> players, SimulationManager simulationManager) {
        secretNumber = (int) (Math.random() * maxNum); // EL NUMERO SECRETO SIEMPRE ES POSITIVO
        this.players = players;
        this.simulationManager = simulationManager;
        round = 0;
        roundState = RoundState.NOT_STARTED;
        isGamesActive = true;
        this.maxNum = maxNum;
    }

    public void runReferee() {
        System.out.println("NUMBERO SECRETO: " + secretNumber + " [LOG]");

        while (isGamesActive) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                isGamesActive = false;
            }

            if (roundState == RoundState.NOT_STARTED) {
                iniciarRonda();
            }
        }

        //System.out.println("EL ARBITRO HA MUERTO");
    }

    private synchronized void skipTurn() {
        if (blockedPlayer != null) {
            blockedPlayer.setSkipTurn(true);
        }
    }

    private synchronized void giveBackTurn() {
        if (blockedPlayer != null) {
            blockedPlayer.setSkipTurn(false);
            blockedPlayer.unlockTurn();
            blockedPlayer = null;
        }
    }

    private void iniciarRonda() {
        roundState = RoundState.IN_PROGRESS;
        round++; // ACTUALIZAR NUMERO DE RONDA

        System.out.println("\n\n=== 🎲 RONDA " + round + " ===\n");

        analizarTurnos(); // CAMBIA EL VALOR DE blockedPlayer
        if (!isGamesActive) {
            return;
        }
        skipTurn(); // QUITAR EL TURNO AL JUGADO R MÁS CERCA DE ADIVINAR EL NÚMERO SECRETO

        roundState = RoundState.NOT_STARTED;
    }

    private void analizarTurnos() {
        int lowerDiference = maxNum;
        int posPlayerToSkip = -1;
        int actualPlayerValue = -1;
        int skipPlayerValue = -1;


        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isSkipTurn()) { // SI EL PLAYER ESTA BLOQUEADO SE LO SALTA
                System.out.println("EL JUGADOR " + players.get(i).getName() + " NO HA PODIDO JUGAR ESTA RONDA 😶‍🌫️");
                continue;
            }

            try {
                actualPlayerValue = players.get(i).getNumberTry().take(); // OBTENER EL NÚMERO QUE HA DICHO EL JUGADOR
            } catch (InterruptedException e) {
                System.out.println("💀 ERROR AL TOMAR numberTry");
            }

            posWinnerPlayer = i; // TODO QUITAR PARA CUANDO EL JUEGO TODAVIA SIGUE

            System.out.println("EL JUGADOR " + players.get(i).getName() + " HA DICHO EL NÚMERO: " + actualPlayerValue);
            verifyNumber(actualPlayerValue);

            if (!isGamesActive) {
                break;
            }

            if (Math.abs(actualPlayerValue-secretNumber) < lowerDiference) {
                lowerDiference = Math.abs(actualPlayerValue-secretNumber);
                posPlayerToSkip = i;
                skipPlayerValue = actualPlayerValue;
            }
        }

        if (!isGamesActive) {
            return;
        }

        giveBackTurn(); // DAR EL TURNO AL JUGADOR QUE ESTABA BLOQUEADO
        //System.out.println("MAX DIFERENCE: " + lowerDiference + " [LOG]");
        blockedPlayer = blockedPlayer == null ? players.get(posPlayerToSkip) : null;
        giveHintToPlayers(skipPlayerValue); // DAR LA PISTA AL RESTO DE JUGADORES
    }

    private void giveHintToPlayers(int hint) {
        for (Player p : players) {
            p.receiveHint(hint);
        }
    }

    public void verifyNumber(int num) {
        if (num == secretNumber) {
            System.out.println("¡Y ES CORRECTO!");
            System.out.println("¡HA GANADO! 😀");
            stopGame();
        } else {
            System.out.println("PERO HA FALLADO 💩");
        }
    }

    public void stopGame() {
        for (Player p : players) { // PRIMERO MANDAMOS SEÑAL DE INTERRUPCIÓN A TODOS LOS HILOS Player
            p.setGamesActive(false);
        }

        roundState = RoundState.FINISHED_GAME;
        isGamesActive = false; // MARCAR EL JUEGO COMO TERMINADO
        simulationManager.setWinner(players.get(posWinnerPlayer));
    }
}
