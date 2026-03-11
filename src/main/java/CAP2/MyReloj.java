package CAP2;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

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
        // Hilo 1
        JFrame ventana = new JFrame("Reloj");
        MyReloj reloj = new MyReloj();

        ventana.add(reloj);
        ventana.setSize(300, 200);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);

        Thread hilo = new Thread(reloj);

        hilo.start();

        // Hilo 2
        JFrame ventana2 = new JFrame("Reloj-2");
        MyReloj reloj2 = new MyReloj();

        ventana2.add(reloj2);
        ventana2.setSize(400, 200);
        ventana2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana2.setVisible(true);

        Thread hilo2 = new Thread(reloj2);
        hilo2.start();

        // Hilo 3
        JFrame ventana3 = new JFrame("Reloj-3");
        MyReloj reloj3 = new MyReloj();

        ventana3.add(reloj3);
        ventana3.setSize(300, 300);
        ventana3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana3.setVisible(true);

        reloj3.start();

        // TODO FINALIZAR TODOS LOS HILOS CREADOS AL CERRAR LAS VENTANAS
        if (!ventana.isDisplayable()) {
            reloj.stop();
        }

        if (!ventana2.isDisplayable()) {
            reloj2.stop();
        }

        if (!ventana3.isDisplayable()) {
            reloj3.stop();
        }
    }
}
