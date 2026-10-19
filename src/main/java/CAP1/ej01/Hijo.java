package CAP1.ej01;

import java.io.*;

public class Hijo {
    public static void main(String[] args) {
        try {
            Thread.sleep(500);
            System.out.println(devolverTextoCifrado(args[0]));
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }

    private static String devolverTextoCifrado(String ruta) {
        String texto = "";
        int c;
        try(FileReader r = new FileReader(ruta)) {
            while ((c = r.read()) != -1) {
                texto = (char) c + texto;
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: NO SE ENCONTRO EL FICHERO. " + e);
        } catch (IOException e) {
            System.out.println("ERROR IOException: " + e);
        }

        return texto;
    }

}

