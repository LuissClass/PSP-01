package CAP2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;

// Muestra la hora actual enb tres ventanas distintas actualizandose en tiempo real

public class MyReloj extends JPanel implements Runnable {
    private Thread hilo = null;
    private String hora = LocalTime.now().toString();
    private boolean running = true;

    public void start() {
        if (hilo == null) {
            hilo = new Thread(this);
            hilo.start();
        }
    }

    @Override
    public void run() {
        while (running) {
            hora = LocalTime.now().withNano(0).toString();
            repaint(); // vuelve a dibujar el panel

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void stop() {
        running = false;

        if (hilo != null) {
                hilo = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.BLUE);
        g.drawString(hora, 50, 100);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static void main(String[] args) {
        // Creacion hilos
        for (int i = 0; i < 3; i++) {
            JFrame ventana = new JFrame("Reloj-3");
            MyReloj reloj = new MyReloj();

            ventana.add(reloj);
            ventana.setSize(300, 200);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.setVisible(true);

            reloj.start();

            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    reloj.stop();
                }
            });
        }
    }
}
