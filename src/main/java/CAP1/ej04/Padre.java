package CAP1.ej04;

import java.io.*;

public class Padre {
    public static void main(String[] args) throws InterruptedException {
        final String hijo = "src/main/java/CAP1/ej04/Hijo.java";
        final int N = 3;
        int[] resultados = new int[N];
        Thread[] hilos = new Thread[N];

        for (int i = 0; i < N; i++) {
            final int id = i;
            hilos[i] = new Thread(() -> {
                try {
                    Process p = new ProcessBuilder("java", hijo, String.valueOf(id + 1)).start();
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                        resultados[id] = Integer.parseInt(br.readLine().trim());
                    }
                    p.waitFor();
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error con hijo " + id + ": " + e);
                }
            });
            hilos[i].start();
        }

        for (Thread t : hilos) t.join(); // esperar a todos

        int suma = 0;
        for (int r : resultados) suma += r;
        System.out.println("SUMA DE CUADRADOS: " + suma);
    }
}