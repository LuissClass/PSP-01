package ies.tema1.examen1;

public class EcharHuevo {
    public static void main(String[] args) {
        try {
            Thread.sleep(500);
            System.out.println("2. Echar el huevo");
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
            Thread.currentThread().interrupt();
        }
    }
}
