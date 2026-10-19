package CAP1.ej02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hijo2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            br.readLine();               // saltamos la primera línea
            String linea = br.readLine(); // segunda línea
            System.out.println(Integer.parseInt(linea.trim()));
        } catch (IOException e) {
            System.err.println("[HIJO 2] ERROR leyendo el fichero: " + e);
            System.exit(1);
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println("[HIJO 2] ERROR: la línea 2 no contiene un número válido");
            System.exit(1);
        }
    }
}