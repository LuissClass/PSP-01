package CAP3.act0302;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int remotePort = 6000;

        try {
            Socket client = new Socket(host, remotePort);
            System.out.println("Nombre HOST/IP: " +  client.getInetAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


