package CAP3.act0302;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 6000;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Escuchando en: " + server.getLocalPort());

            Socket cliente1 = server.accept();
            System.out.println("Cliente conectado: " + cliente1.getInetAddress().getHostAddress());
            System.out.println("Local port: " + cliente1.getLocalPort());
            System.out.println("Remote port: " + cliente1.getPort());

            System.out.println();

            Socket cliente2 = server.accept();
            System.out.println("Cliente conectado: " + cliente2.getInetAddress().getHostAddress());
            System.out.println("Local port: " + cliente2.getLocalPort());
            System.out.println("Remote port: " + cliente2.getPort());

            cliente1.close();
            cliente2.close();
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// UN FORMA MÁS MODERNA
class Server_2 {
    public static void main(String[] args) {
        int port = 6000;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Escuchando en: " + server.getLocalPort());

            Socket cliente1 = server.accept();
            Thread.startVirtualThread(() -> processClient(cliente1));

            Socket cliente2 = server.accept();
            Thread.startVirtualThread(() -> processClient(cliente2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void processClient(Socket client) {
        System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
        System.out.println("Local port: " + client.getLocalPort());
        System.out.println("Remote port: " + client.getPort());

        System.out.println();
    }
}
