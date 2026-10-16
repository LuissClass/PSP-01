package ies.tema1.practica1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", args[0], args[1]);
        Process p;
        try {
            p = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()))
        ) {
            String res = "";
            String line = "";
            String error = "ERRORES DEL HIJO: \n";
            String errorLine = "";

            while ((line = br.readLine()) != null) {
                res += line + "\n";
            }

            while ((errorLine = brError.readLine()) != null) {
                error += "\t" + errorLine + "\n";
            }

            int exitCode = p.waitFor();

            if (exitCode == 0) {
                System.out.println("EL HIJO TERMINO CORRECTAMENTE");
                System.out.println("EL Padre HA RECIBIDO EL MENSAJE: " + res);
            } else {
                System.out.println("HUBO UN ERROR CON EL HIJO");
                System.out.println(error);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
}