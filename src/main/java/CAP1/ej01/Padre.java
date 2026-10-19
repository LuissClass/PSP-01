package CAP1.ej01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {
    public static void main(String[] args) {
        final String hijo = "src/main/java/CAP1/ej01/Hijo.java";
        final String msg = "src/main/java/CAP1/ej01/msg.txt";

        ProcessBuilder pb = new ProcessBuilder("java", hijo, msg);
        Process p;
        try {
            p = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()))
        ) {
            StringBuilder res = new StringBuilder();
            String line = "";
            StringBuilder error = new StringBuilder("ERRORES DEL HIJO: \n");
            String errorLine = "";

            while ((line = br.readLine()) != null) {
                res.append(line).append("\n");
            }

            while ((errorLine = brError.readLine()) != null) {
                error.append("\t").append(errorLine).append("\n");
            }

            int exitCode = p.waitFor();

            if (exitCode == 0) {
                System.out.println("EL HIJO TERMINO CORRECTAMENTE");
                System.out.println("EL Padre HA RECIBIDO EL MENSAJE: " + res);
            } else {
                System.out.println("HUBO UN ERROR CON EL HIJO");
                System.out.println(error);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}