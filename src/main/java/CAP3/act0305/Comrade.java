package CAP3.act0305;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Comrade {
    public static void main(String[] args) {
        String host = "localhost";
        int remotePort = 6000;

        try {
            Socket comrade = new Socket(host, remotePort);
            System.out.println("HELLO COMRADE FROM PORT " +  comrade.getLocalPort());

            comunicationBeetwenBigBrother(comrade);
        } catch (IOException e) {
            System.out.println("ERROR IN COMRADE CONEXION");
        } finally {
            System.out.println("GOOD BYE");
        }
    }

    private static void comunicationBeetwenBigBrother(Socket comrade) {
        InputStream input = null;
        DataInputStream inputFlow = null;
        OutputStream output = null;
        DataOutputStream outputFlow = null;

        try {
            // CREATE OUTPUT FLOW OF COMRADE
            output = comrade.getOutputStream();
            outputFlow = new DataOutputStream(output);

            // SEND MESAGGE TO BIG BROTHER
            outputFlow.writeInt(response());

            System.out.println("PLEASE WAIT FOR BIG BROTHER'S MESSAGE");

            // CREATE INPUT FLOW TO big brother
            input = comrade.getInputStream();
            inputFlow = new DataInputStream(input);

            // BIG BROTHER MESSAGE
            System.out.println("BIG BROTHER SAYS: \n\t" + inputFlow.readUTF());

        } catch (IOException e) {
            System.out.println("ERROR IN COMRADE'S COMUNICATION");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (inputFlow != null) {
                    inputFlow.close();
                }
                if (output != null) {
                    output.close();
                }
                if (outputFlow != null) {
                    outputFlow.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR WHEN CLOSING COMRADE SOCKETS");
            }
        }
    }

    private static int response() {
        Scanner scanner = new Scanner(System.in);
        int value;

        do {
            System.out.println("HOW MUCH DO YOU LOVE BIG BROTHER? (0-10)");
            value = scanner.nextInt();

            if  (value < 0 || value > 10) {
                System.out.println("INVALID VALUE. ARE YOU WINSTON?");
            }
        } while (value < 0 || value > 10);
        return value;
    }
}
