package CAP2.prioridad01;

class Contador implements Runnable {

        private String nombre;
        private long contador = 0;

        public Contador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {

            long inicio = System.currentTimeMillis();

            while (System.currentTimeMillis() - inicio < 1000) {
                contador++;
            }

            System.out.println(nombre + " ha contado: " + contador);
        }

    public static void main(String[] args) throws InterruptedException {

        Thread hilo1 = new Thread(new Contador("HILO 1"));
        Thread hilo2 = new Thread(new Contador("HILO 2"));
        Thread hilo3 = new Thread(new Contador("HILO 3"));

        hilo1.setPriority(1);
        hilo2.setPriority(5);
        hilo3.setPriority(10);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        hilo1.join();
        hilo2.join();
        hilo3.join();

        System.out.println("TODOS LOS HILOS HAN TERMINADO");
    }
}
