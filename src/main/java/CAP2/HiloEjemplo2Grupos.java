package CAP2;

public class HiloEjemplo2Grupos extends Thread{
    @Override
    public void run() {
        System.out.println(
                "Informacion del hilo: " + Thread.currentThread()
        );

        System.out.println(
                Thread.currentThread().getName() + " Finalizando ejecucion."
        );
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Principal");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread());

        ThreadGroup grupo = new ThreadGroup("Grupo de hilos");
        HiloEjemplo2Grupos h = new HiloEjemplo2Grupos();

        Thread h1 = new Thread(grupo, h, "Hilo1");
        Thread h2 = new Thread(grupo, h, "Hilo2");
        Thread h3 = new Thread(grupo, h, "Hilo3");

        h1.start(); h2.start(); h3.start();

        System.out.println("3 HILOS CREADOS");
        System.out.println(">>Hilos activos: " + Thread.activeCount());
    }
}
