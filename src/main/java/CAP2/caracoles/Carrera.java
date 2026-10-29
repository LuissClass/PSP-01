package CAP2.caracoles;

public class Carrera {
    private final int distancia;
    private volatile boolean finalizada;

    public Carrera(int distancia) {
        this.distancia = distancia;
        finalizada = false;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Falta el argumento DISTANCIA de la carrera");
            return;
        }

        Carrera carrera = new Carrera(Integer.parseInt(args[0]));
        new Thread(new Caracol(carrera, "Hamburguesa", "🍔")).start();
        new Thread(new Caracol(carrera, "Gato", "😹")).start();
        new Thread(new Caracol(carrera, "Pez", "️🐟")).start();
        new Thread(new Caracol(carrera, "Caballo", "🐴")).start();
        new Thread(new Caracol(carrera, "Perro", "🐶")).start();
    }

    public synchronized boolean declararGanador(String nombre, String emogi) {
        if (!finalizada) {
            finalizada = true;
            System.out.println("\n=========CLASIFICACION=========");
            System.out.println("¡El ganador es " + nombre + " " + emogi  + "!");
            return true;
        }
        return false;
    }

    public int getDistancia() {
        return distancia;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}
