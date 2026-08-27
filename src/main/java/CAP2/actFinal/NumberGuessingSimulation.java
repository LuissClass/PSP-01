package CAP2.actFinal;



/**
 * SIMULADOR ADIVINAR NÚMERO
 * A nivel general se basa en que cada jugador, representado por la clase Jugador y que corre en un hilo,
 * debe ir acercandose cada vez más al número secreto que el arbitro ha generado. El primero que lo "adivine" gana.
 * Debe haber un limite de tiempo que se puede configurar para cada partida
 * (EJERCICIO HECHO PARA PRACTICAR CONCURRENCIA.)
 */

public class NumberGuessingSimulation {
    public static void main(String[] args) {
        SimulationManager manager = new SimulationManager();
        manager.run();
    }
}
