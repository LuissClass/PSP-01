package ies.tema1.examen2;

import java.io.FileWriter;
import java.io.IOException;

public class DatNorteOeste {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(args[0], true)) {
            fw.write("DAT-SUR-ESTE: 10:00am o 16:00pm\n");
        } catch (IOException e) {
            // Error de escritura.
        }
    }
}
