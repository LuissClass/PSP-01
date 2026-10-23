package ies.tema1.examen1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {

    public static void main(String[] args) {

        System.out.println("RECETA PARA HACER UN HUEVO FRITO");
        System.out.println("============================");

        ejecutarProceso("CalentarAceite");
        ejecutarProceso("EcharHuevo");
        ejecutarProceso("EcharSal");

        System.out.println("============================");
        System.out.println("El padre saca y come el huevo.");
    }

    private static void ejecutarProceso(String clase) {

        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                "ies.tema1.examen1." + clase
        );

        try {
            Process p = pb.start();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                 BufferedReader brError = new BufferedReader(
                         new InputStreamReader(p.getErrorStream()))) {

                String linea;

                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }

                StringBuilder errores = new StringBuilder();

                while ((linea = brError.readLine()) != null) {
                    errores.append(linea).append("\n");
                }

                int codigo = p.waitFor();

                if (codigo == 0) {
                    System.out.println(
                            "El proceso hijo " + clase
                                    + " terminó correctamente."
                    );
                } else {
                    System.out.println(
                            "Error en el proceso hijo " + clase
                    );
                    System.out.println(errores);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al ejecutar el proceso: " + e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El proceso padre fue interrumpido.");
        }
    }
}