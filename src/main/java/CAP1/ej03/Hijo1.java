package CAP1.ej03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hijo1 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            int n1 = Integer.parseInt(br.readLine().trim());
            int n2 = Integer.parseInt(br.readLine().trim());

            System.err.println("[HIJO 1] Leí " + n1 + " y " + n2 + " del fichero. Se los envío al Hijo 2");

            // Esto viaja por el pipe hasta la entrada del Hijo 2
            System.out.println(n1);
            System.out.println(n2);
        } catch (IOException e) {
            System.err.println("[HIJO 1] ERROR leyendo el fichero: " + e);
            System.exit(1);
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println("[HIJO 1] ERROR: el fichero debe tener 2 líneas con números válidos");
            System.exit(1);
        }
    }
}