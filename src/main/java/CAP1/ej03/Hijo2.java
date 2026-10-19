package CAP1.ej03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hijo2 {
    public static void main(String[] args) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            int n1 = Integer.parseInt(entrada.readLine().trim());
            int n2 = Integer.parseInt(entrada.readLine().trim());

            System.err.println("[HIJO 2] Recibí " + n1 + " y " + n2 + " del Hijo 1");

            // Resultado para el padre
            System.out.println("SUMA: " + n1 + " + " + n2 + " = " + (n1 + n2));
        } catch (IOException e) {
            System.err.println("[HIJO 2] ERROR de lectura: " + e);
            System.exit(1);
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println("[HIJO 2] ERROR: no recibí 2 números válidos");
            System.exit(1);
        }
    }
}