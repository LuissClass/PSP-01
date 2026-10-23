package ies.tema1.examen2;

import java.io.FileWriter;
import java.io.IOException;

public class Padre {

    public static void main(String[] args) {

        String fichero = "proxima-acción.txt";

        try {
            // EL COMITE ESCRIBE LA ACCION Y CREA EL FICHERO
            try (FileWriter fw = new FileWriter(fichero)) {
                fw.write("Manifestación\n");
            }

            ejecutarProceso("DatSurEste", fichero);
            ejecutarProceso("DatNorteOeste", fichero);
            ejecutarProceso("DatCentro", fichero);

        } catch (IOException e) {
            // Error de escritura.
        }
    }

    private static void ejecutarProceso(String clase, String fichero) {

        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                clase,
                fichero
        );

        try {
            Process p = pb.start();
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
}