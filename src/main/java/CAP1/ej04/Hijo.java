package CAP1.ej04;

public class Hijo {
    public static void main(String[] args) throws InterruptedException {
        int n = Integer.parseInt(args[0]);
        Thread.sleep((long) (Math.random() * 1000));
        System.out.println(n * n);
    }
}