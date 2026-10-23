package ies.tema1.examen1;

public class EcharSal {
    public static void main(String[] args) {
        try {
            Thread.sleep(500);
            System.out.println("3. Echar sal");
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
            Thread.currentThread().interrupt();
        }
    }
}
