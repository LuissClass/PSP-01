package CAP1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Ejem5 {
    public static void main(String[] args) throws IOException {
        File dir = new File(".\\target\\classes");
        ProcessBuilder pb = new ProcessBuilder("java", "CAP1.EjemploLectura");
        pb.directory(dir);

        Process p = pb.start();

        OutputStream os = p.getOutputStream();
        os.write("HOOLAA MAANUUEEL!\n".getBytes());
        os.flush();

        InputStream is = p.getInputStream();
        int c;
        while((c = is.read()) != -1) {
            System.out.print((char) c);
        }
        is.close();

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
