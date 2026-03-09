package CAP1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Ejem4 {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder("CMD", "/C", "DATE").start();

        OutputStream os = p.getOutputStream();
        os.write("15-06-18".getBytes());
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