package followup.server;

import followup.server.threads.ClientConnectionThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FollowUpServer {

    static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        System.out.println("Server has been initiated.\n");
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket.\n");
            throw new RuntimeException();
        }
        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New client connected!\n");
            new Thread(new ClientConnectionThread(clientSocket)).start();
        }
    }

}
