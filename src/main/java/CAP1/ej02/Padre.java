package CAP1.ej02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {
    public static void main(String[] args) {
        final String hijo1 = "src/main/java/CAP1/ej02/Hijo1.java";
        final String hijo2 = "src/main/java/CAP1/ej02/Hijo2.java";
        final String fichero = "src/main/java/CAP1/ej02/numeros.txt";

        try {
            int n1 = lanzarHijo(hijo1, fichero);
            int n2 = lanzarHijo(hijo2, fichero);

            System.out.println("EL PADRE HA RECIBIDO: " + n1 + " y " + n2);
            System.out.println("SUMA: " + n1 + " + " + n2 + " = " + (n1 + n2));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int lanzarHijo(String clase, String fichero) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", clase, fichero);
        // Los errores del hijo se ven directamente en la consola del padre
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process p = pb.start();

        StringBuilder res = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("HUBO UN ERROR CON EL HIJO (" + clase + ")");
        }

        return Integer.parseInt(res.toString().trim());
    }
}