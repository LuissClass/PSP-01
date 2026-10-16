package ies.tema1;

import java.io.IOException;

public class Ej1 {
    public static void main(String[] args) {
        try {
            System.out.println("Lanzando Notepad...");

            ProcessBuilder pb = new ProcessBuilder("Notepad", "-new-window");
            Process p = pb.start();

            System.out.println("Notepad lanzado");
            System.out.println("PID: " + p.pid());

            int exitVal = p.waitFor();

            System.out.println("El proceso ha terminado");
            System.out.println("Exit val: " + exitVal);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
