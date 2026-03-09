package CAP1.act0108;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) throws IOException {
        File fIn = new File("src\\main\\java\\CAP1\\act0108\\entrada.txt");
        File fOut = new File("src\\main\\java\\CAP1\\act0108\\salida.txt");

        File dir = new File(".\\target\\classes");
        ProcessBuilder pb = new ProcessBuilder("java", "CAP1.EjemploLectura");
        pb.directory(dir);

        pb.redirectInput(ProcessBuilder.Redirect.from(fIn));

        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        Process p = pb.start();

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
