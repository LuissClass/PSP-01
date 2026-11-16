package CAP2.juego01;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    public static void main(String[] args) throws InterruptedException {
        Pantalla pantalla = new Pantalla();

        Renderizar render = new Renderizar(pantalla);
        Evento vida = new Evento(pantalla, Evento.Tipo.VIDA);
        Evento puntos = new Evento(pantalla, Evento.Tipo.PUNTOS);
        Monstruo monstruo = new Monstruo(pantalla);

        render.setPriority(Thread.MAX_PRIORITY);     // máxima
        vida.setPriority(Thread.NORM_PRIORITY);      // normal
        puntos.setPriority(Thread.NORM_PRIORITY);    // normal
        monstruo.setPriority(Thread.MIN_PRIORITY);   // baja

        render.start();
        vida.start();
        puntos.start();
        monstruo.start();

        render.join();
    }
}

// Recurso compartido: todos sus métodos son synchronized.
class Pantalla {
    static final int ANCHO = 10; // casillas; el player está en la 0
    private int vida = 100;
    private int puntos = 0;
    private int siguienteMonstruo = ANCHO - 1;
    private boolean fin = false;
    private final List<Integer> monstruos = new ArrayList<>();

    public synchronized void setSiguienteMonstruo(int pos) {
        siguienteMonstruo = pos;
    }

    public synchronized void anadirMonstruo() {
        monstruos.add(siguienteMonstruo);
    }

    // Mueve los monstruos hacia el player; se paran en la casilla 1 (pegados a él).
    public synchronized void moverMonstruos() {
        for (int i = 0; i < monstruos.size(); i++) {
            if (monstruos.get(i) > 1) {
                monstruos.set(i, monstruos.get(i) - 1);
            }
        }
    }

    // Los monstruos que han llegado al player le quitan vida.
    public synchronized void recibirAtaques() {
        for (int pos : monstruos) {
            if (pos == 1) vida -= 10;
        }
        if (vida <= 0) {
            vida = 0;
            fin = true;
        }
    }

    public synchronized void sumarPuntos(int p) {
        if (!fin) puntos += p;
    }

    public synchronized boolean isFin() {
        return fin;
    }

    public synchronized String dibujar() {
        StringBuilder sb = new StringBuilder();
        sb.append("❤️  Vida: ").append(vida)
                .append("   ⭐ Puntos: ").append(puntos)
                .append("   👾 Siguiente monstruo en casilla: ").append(siguienteMonstruo)
                .append("\n\n");

        for (int x = 0; x < ANCHO; x++) {
            if (x == 0) {
                sb.append(fin ? "💀" : "🧙");
                continue;
            }
            sb.append(monstruos.contains(x) ? (x == 1 ? "👹" : "👾") : "⬛");
        }

        sb.append("\n");
        if (fin) sb.append("\n☠️  GAME OVER\n");
        return sb.toString();
    }
}

// Hilo de máxima prioridad: dibuja la pantalla por stdout.
class Renderizar extends Thread {
    private final Pantalla pantalla;

    Renderizar(Pantalla p) {
        pantalla = p;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("\033[H\033[2J"); // limpiar consola
            System.out.println(pantalla.dibujar());
            System.out.flush();

            if (pantalla.isFin()) break;
            dormir(200);
        }
    }

    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}

// Hilo de prioridad normal con 2 tipos: actualiza vida o puntos.
class Evento extends Thread {
    enum Tipo { VIDA, PUNTOS }

    private final Pantalla pantalla;
    private final Tipo tipo;

    Evento(Pantalla p, Tipo t) {
        pantalla = p;
        tipo = t;
    }

    @Override
    public void run() {
        while (!pantalla.isFin()) {
            if (tipo == Tipo.VIDA) {
                pantalla.recibirAtaques();
            } else {
                pantalla.sumarPuntos(1);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }
}

// Hilo de prioridad baja: calcula la posición del siguiente monstruo, lo crea y los mueve.
class Monstruo extends Thread {
    private final Pantalla pantalla;

    Monstruo(Pantalla p) {
        pantalla = p;
    }

    @Override
    public void run() {
        int tick = 0;

        while (!pantalla.isFin()) {
            if (tick % 10 == 0) {
                // Cada 10 x 500 ms = 5 segundos
                pantalla.setSiguienteMonstruo(Pantalla.ANCHO - 1); // entra por la derecha
                pantalla.anadirMonstruo();
            }

            pantalla.moverMonstruos();
            tick++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }
}