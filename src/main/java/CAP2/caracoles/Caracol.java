package CAP2.caracoles;

public class Caracol implements Runnable{
    private int distanciaRecorrida;
    private final Carrera carrera;
    private final String name;
    private final String emogi;

    public Caracol(Carrera carrera, String name, String emogi) {
        this.carrera = carrera;
        this.name = name;
        this.emogi = emogi;
    }

    @Override
    public void run() {
        while (true) {
            if (carrera.isFinalizada()) {
                System.out.println(name + " ha parado (" + distanciaRecorrida + "m).");
                return;
            }

            int avance = devolverAvance();
            distanciaRecorrida += avance;

            dibujarPista();

            if (distanciaRecorrida >= carrera.getDistancia()) {
                carrera.declararGanador(name);
            }

            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int devolverAvance() {
        return (int) (Math.random() * 5) + 1;
    }

    private void dibujarPista() {
        StringBuilder pista = new StringBuilder();

        pista.append(String.format("%-10s [", name)); // Nombre alineado

        for (int i = 0; i < carrera.getDistancia(); i++) {

            if (i == distanciaRecorrida) {

                pista.append(emogi); // La posición del caracol

            } else {

                pista.append("_"); // La pista vacía

            }

        }

        pista.append("] Meta!");

        System.out.println(pista.toString());
    }
}
