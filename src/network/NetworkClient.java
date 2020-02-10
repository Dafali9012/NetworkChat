package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkClient implements Runnable{
    private static final NetworkClient instance = new NetworkClient();
    private final String SERVER_IP = "localhost";
    private final int MSG_SIZE = 512;
    private final int SLEEP_MS = 100;

    private DatagramSocket socket;
    private InetAddress serverAddress;

    private boolean isRunning = true;

    private NetworkClient(){
        try {
            serverAddress = InetAddress.getByName(SERVER_IP);
            socket = new DatagramSocket(0);
            socket.setSoTimeout(SLEEP_MS);
        } catch(Exception e){ System.out.println(e.getMessage()); }
    }

    public void sendMsgToServer(String msg) {
        byte[] buffer = msg.getBytes();
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, this.serverAddress, NetworkServer.getInstance().PORT);
        try { socket.send(request); } catch (Exception e) {}
    }

    private void receiveMessageFromServer() {
        byte[] buffer = new byte[MSG_SIZE];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(response);
            String serverMsg = new String(buffer, 0, response.getLength());
            System.out.println(serverMsg); // debugging purpose only!
            // TODO: Save the msg to a queue instead
        } catch (Exception ex) {
            try { Thread.sleep(SLEEP_MS); }
            catch (Exception e) {}
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            receiveMessageFromServer();
        }
    }

    public static NetworkClient getInstance() {
        return instance;
    }
}