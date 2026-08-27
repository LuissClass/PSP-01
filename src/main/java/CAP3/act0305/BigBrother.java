package CAP3.act0305;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

// EL PROGRAMA DEBE HACER QUE HAYA n CLIENTES Y QUE CADA CLIENTE VOTE SI APOYA O NO AL BIG BROTHER. AL FINAL SE VEN LOS RESULTADOS
// A QUIENES APOYAN AL BIG BROTHER EL BigBrother LES ENVIA UN MENSAJE DICIENDOLES SI LOS MANDAN A REACONDICIONAMIENTO O LOS DEJAN CHILL
public class BigBrother {
    private final static int port = 6000;

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("NUMBER OF COMRADES WAITING FOR CONEXTION WITH BIG BROTHER: ");
        int numberOfComrades = scanner.nextInt();
        ServerSocket bigBrother = null;
        Socket[] comrades = new Socket[numberOfComrades];

        try {
            bigBrother = new ServerSocket(port);

            // ENTABLISH CONEXION BEETWEN ALL COMRADES
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < numberOfComrades; i++) {
                    Socket comrade = bigBrother.accept();
                    comrades[i] = comrade;

                    executor.submit(() -> comunicationBeetwenComrade(comrade));
                    System.out.println("COMRADE CONNECTED. PORT: " + comrade.getPort());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR STARTING SOCKETS");
        } finally { // TODO SOLUCIONAR ERROR
            scanner.close();

            Arrays.stream(comrades).forEach(comrade -> {
                try {
                    comrade.close();
                } catch (IOException e) {
                    System.out.println("ERROR WHEN CLOSING COMRADE SOCKETS");
                }
            });

            try {

                bigBrother.close();
            } catch (IOException e) {
                System.out.println("ERROR WHEN CLOSING BIG BROTHER SOCKET");
            }

            System.out.println("GOOD BYE");
        }
    }

    private static void comunicationBeetwenComrade(Socket comrade) {
        InputStream input = null;
        DataInputStream inputFlow = null;
        OutputStream output = null;
        DataOutputStream outputFlow = null;

        try {
            // CREATE INPUT FLOW OF COMRADE
            input = comrade.getInputStream();
            inputFlow = new DataInputStream(input);

            // COMRADE MESSAGE
            int comradeValue = inputFlow.readInt();
            System.out.println("COMRADE " + comrade.getPort() + " SAYS THAT HE LOVES BIG BROTHER " +  comradeValue);

            // CREATE OUTPUT FLOW TO COMRADE
            output = comrade.getOutputStream();
            outputFlow = new DataOutputStream(output);

            // BIG BROTHER SENDS A MESSAGE
            outputFlow.writeUTF(bigBrotherResponse(comradeValue));

        } catch (IOException e) {
            System.out.println("ERROR IN BIG BROTHER'S COMUNICATION");
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
                System.out.println("ERROR AL CERRAR LOS FLUJOS DE ENTRADA");
            }
        }
    }

    private static String bigBrotherResponse(int comradeValue) {
        return switch (comradeValue) {
            case 0,1,2,3,4,5,6,7,8 -> "YOU DO NOT LOVE BIG BROTHER; YOU GO TO ROOM 101";
            case 9 ->  "BIG BROTHER IS WHATCHING YOU";
            case 10 -> "BIG BROTHER HATES AND LOVES YOU; YOU ARE FREE TO GO";
            default -> throw new IllegalStateException("Unexpected value: " + comradeValue);
        };
    }
}
