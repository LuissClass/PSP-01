package CAP1.ej03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Padre {
    public static void main(String[] args) {
        final String hijo1 = "src/main/java/CAP1/ej03/Hijo1.java";
        final String hijo2 = "src/main/java/CAP1/ej03/Hijo2.java";
        final String fichero = "src/main/java/CAP1/ej03/numeros.txt";

        // Solo Hijo1 recibe la ruta del fichero
        ProcessBuilder pb1 = new ProcessBuilder("java", hijo1, fichero);
        ProcessBuilder pb2 = new ProcessBuilder("java", hijo2);
        pb1.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb2.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            // stdout de Hijo1 -> stdin de Hijo2
            List<Process> procesos = ProcessBuilder.startPipeline(List.of(pb1, pb2));
            Process ultimo = procesos.get(procesos.size() - 1);

            StringBuilder res = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ultimo.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    res.append(line).append("\n");
                }
            }

            boolean todoOk = true;
            for (Process p : procesos) {
                if (p.waitFor() != 0) {
                    todoOk = false;
                }
            }

            if (todoOk) {
                System.out.println("LOS HIJOS TERMINARON CORRECTAMENTE");
                System.out.println("EL PADRE HA RECIBIDO: " + res);
            } else {
                System.out.println("HUBO UN ERROR EN ALGUNO DE LOS HIJOS");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}