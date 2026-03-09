package CAP1.act0107;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        File fIn = new File("src\\main\\java\\CAP1\\act0107\\entrada.txt");
        File fOut = new File("src\\main\\java\\CAP1\\act0107\\salida.txt");
        File fErr = new File("src\\main\\java\\CAP1\\act0107\\error.txt");

        File dir = new File(".\\target\\classes");
        ProcessBuilder pb = new ProcessBuilder("java", "CAP1.EjemploLectura");
        pb.directory(dir);

        pb.redirectInput(fIn);
        pb.redirectOutput(fOut);
        pb.redirectError(fErr);

        Process p = pb.start();

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
