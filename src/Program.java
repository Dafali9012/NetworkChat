import network.NetworkClient;
import network.NetworkServer;

import java.util.Scanner;

public class Program {
    private static final Program instance = new Program();

    private Program() {
        Thread clientThread = new Thread(NetworkClient.getInstance());
        Thread serverThread = new Thread(NetworkServer.getInstance());
        serverThread.start();
        clientThread.start();

        Scanner scan = new Scanner(System.in);
        while(true) {
            NetworkClient.getInstance().sendMsgToServer("Dafali: "+scan.nextLine());
        }
    }

    public static Program getInstance() {
        return instance;
    }
}
