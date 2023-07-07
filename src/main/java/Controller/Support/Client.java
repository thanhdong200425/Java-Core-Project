package Controller.Support;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket client;
    DataInputStream readFromServer;
    DataOutputStream writeToServer;

    void connectToServer(String host, int port) {
        try {
            // Create a client
            client = new Socket(host, port);
            readFromServer = new DataInputStream(client.getInputStream());
            writeToServer = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void sendMessage(String message) {
        try {
            writeToServer.writeUTF(message);
            writeToServer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void disconnect() {
        try {
            if (client != null) {
                readFromServer.close();
                client.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
