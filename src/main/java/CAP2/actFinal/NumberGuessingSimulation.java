package CAP2.actFinal;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * SIMULADOR ADIVINAR NÚMERO
 * A nivel general se basa en que cada jugador, representado por la clase Jugador y que corre en un hilo,
 * debe ir acercandose cada vez más al número secreto que el arbitro ha generado. El primero que lo "adivine" gana.
 * -
 * Arbitro: Genera el número aleatorio a adivinar, gestiona el bloqueo/reanudo de turnos.
 * + Le quita un turno al jugador que haya estado más cerca del número secreto
 * Jugador: Gestiona la respuesta del jugador, y la forma de adivinar el número secreto.
 * + Dependiendo del numero del jugador que ha estado más cerca cambiará su siguiente jugada
 * Cada respuesta del jugador en cada ronda afectara a la decisión de los players después.
 * Cada respuesta de cada jugador debe bloquear a los hilos de los players y del árbitro hasta que dicha respuesta se genere.
 * -
 * Cada interacción tiene una espera de 500 ms antes de realizarse
 * -
 * Existe un tiempo límite de 30 startSecond para que finalice el juego. Para ello debe existir un solo hilo que vaya indicando la hora.
 *
 */


public class NumberGuessingSimulation implements Runnable {

    // SE PUEDEN CAMBIAR DEPENDIENDO DE LA PARTIDA
    private static final int MAX_NUMBER = 10;
    private static final int MAX_TIME_SECONDS = 4;

    public static void main(String[] args) {
        NumberGuessingSimulation simulacion = new NumberGuessingSimulation();
        Thread s = new Thread(simulacion);
        s.start();

        try {
            s.join();
        } catch (InterruptedException e) {
            s.interrupt();
        }
        System.out.println(" >> LA SIMULACIÓN HA TERMINADO - BYE BYE");
    }

    @Override
    public void run() {
        Time time = new Time();

        List<Player> players = new ArrayList<>(List.of(
                new Player("Jugador 1"),
                new Player("Jugador 2"),
                new Player("Jugador 3")
        ));

        Referee r = new Referee(MAX_NUMBER, players);

        for (Player player : players) {
            player.start();
        }

        r.start();
        time.start();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (time.getCurrentTime() >= MAX_TIME_SECONDS) {
                r.stopGame();
                time.interrupt();

                Thread.currentThread().interrupt();

                System.out.println("SE ACABO EL TIEMPO (" + (time.getCurrentTime()) + "s)");
                break;
            }
        }


        try { // ESPERAR A FINALIZAR A LOS HILOS HIJOS
            for (Player player : players) {
                player.join();
            }
            r.join();
            time.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}






