package ies.tema1.examen1;

public class CalentarAceite {
    public static void main(String[] args) {
        try {
            Thread.sleep(500);
            System.out.println("1. Calentar el aceite");
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
            Thread.currentThread().interrupt();
        }
    }
}
