package CAP2;

public class HiloEjemplo2 extends Thread{
    @Override
    public void run() {
        System.out.println(
                "Dentro de un hilo: " + Thread.currentThread().getName() +
                "\n\tPrioridad: " + Thread.currentThread().getPriority() +
                "\n\tId: " + Thread.currentThread().getId() +
                "\n\tHilos activos con size: " + Thread.getAllStackTraces().keySet().size()
        );
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Principal");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread());
        System.out.println("---");

        HiloEjemplo2 h = null;
        int numHilos = 10;

        for (int i = 0; i < numHilos; i++) {
            h = new HiloEjemplo2();
            h.setName("HILO-"+i);
            h.setPriority(i+1);
            h.start();
            System.out.println(
                    "Informacion del " + h.getName() + ": " + h
            );

            System.out.println(">>> " + numHilos + " HILOS CREADOS");
            System.out.println("Hilos activos con count: " + Thread.activeCount());
            System.out.println();
        }
    }
}
