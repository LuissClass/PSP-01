package CAP1.act0104;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) throws IOException {
        File dir = new File(".\\target\\classes");

        // El tercer parametro es el argumento de LeerNombre.java
        ProcessBuilder pb = new ProcessBuilder("java", "CAP1.act0104.LeerNombre", "Segismundo");

        pb.directory(dir);

        System.out.printf("Directorio de trabajo: %s%n", pb.directory());

        Process p = pb.start();

        try {
            InputStream is = p.getInputStream();
            int c;
            while((c = is.read()) != -1) {
                System.out.print((char) c);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
