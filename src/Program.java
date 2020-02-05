import network.NetworkClient;
import network.NetworkServer;

import java.util.Scanner;

public class Program {
    private static final Program instance = new Program();

    private Program() {
        Thread clientThread = new Thread(NetworkClient.getInstance());
        Thread serverThread = new Thread(NetworkServer.getInstance());
        clientThread.start();
        //serverThread.start();
        Scanner scan = new Scanner(System.in);
        NetworkClient.getInstance().sendMsgToServer("Hej Enkel");
    }

    public static Program getInstance() {
        return instance;
    }
}
