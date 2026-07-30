package CAP2;

class HiloJoin extends Thread {

    private int limite;

    public HiloJoin(String nombre, int limite) {
        super(nombre);
        this.limite = limite;
    }

    @Override
    public void run() {
        System.out.println("Comienza " + getName());

        for (int i = 0; i <= limite; i++) {
            System.out.println(getName() + ": " + i);
        }

        System.out.println("Termina " + getName());
    }

    public static void main(String[] args) {

        HiloJoin h1 = new HiloJoin("Hilo 1", 1);
        HiloJoin h2 = new HiloJoin("Hilo 2", 5);
        HiloJoin h3 = new HiloJoin("Hilo 3", 10);

        h1.start();
        h2.start();
        h3.start();

        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Todos los hilos han terminado. Fin del programa.");
    }
}