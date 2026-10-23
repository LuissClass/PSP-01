package ies.tema1.examen2;

import java.io.FileWriter;
import java.io.IOException;

public class DatCentro {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(args[0], true)) {
            fw.write("Atocha-sol\n");
        } catch (IOException e) {
            // Error de escritura.
        }
    }
}
