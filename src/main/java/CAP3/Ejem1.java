package CAP3;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ejem1 {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");

            mostrarInfo(inetAddress);
            mostrarDireccionesIp(inetAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mostrarInfo(InetAddress inet) {
        System.out.println("getByName() / toString(): " + inet);

        System.out.println("HOST: " + inet.getHostName());
        System.out.println("IP: " + inet.getHostAddress());
        System.out.println("CANONICAL HOST NAME: " + inet.getCanonicalHostName());
        try {
            System.out.println("\nLOCAL HOST: " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mostrarDireccionesIp(InetAddress inet) {
        try {
            InetAddress[] inets = InetAddress.getAllByName(inet.getHostName());
            System.out.println("IP ADDRESS FOR " + inet.getHostName() + ":");
            for (InetAddress i: inets) {
                System.out.println("\t" + i.getHostAddress());
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}


