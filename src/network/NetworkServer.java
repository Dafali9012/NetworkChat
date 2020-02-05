package network;

import java.net.*;

public class NetworkServer implements Runnable {
    private static final NetworkServer instance = new NetworkServer();
    public final int PORT = 80;
    private final int SLEEP_MS = 100;
    private final int MSG_SIZE = 512;
    private DatagramSocket socket;

    private boolean isRunning = true;

    private NetworkServer() {
        try {
            socket = new DatagramSocket(PORT);
            socket.setSoTimeout(SLEEP_MS);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }


    public void sendMsgToClient(String msg, SocketAddress clientSocketAddress) {
        byte[] buffer = msg.getBytes();

        DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientSocketAddress);

        try {
            socket.send(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (isRunning) {
            DatagramPacket clientRequest = new DatagramPacket(new byte[MSG_SIZE], MSG_SIZE);
            //System.out.println("server thread");

            if (!receiveMsgFromAnyClient(clientRequest)) {
                continue;
            }

            String clientMsg = new String(clientRequest.getData(), 0, clientRequest.getLength());
            System.out.println(clientMsg); // debugging purpose only!
            // TODO: Save the msg to a queue instead



            sendMsgToClient("Hej hopp", clientRequest.getSocketAddress());
        }
    }

    private boolean receiveMsgFromAnyClient(DatagramPacket clientRequest) {
        try {
            socket.receive(clientRequest);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static NetworkServer getInstance() {
        return instance;
    }
}
