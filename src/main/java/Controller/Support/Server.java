package Controller.Support;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            Socket client = server.accept();

            DataInputStream readFromClient = new DataInputStream(client.getInputStream());
            DataOutputStream writeToClient = new DataOutputStream(client.getOutputStream());
            BufferedReader enter = new BufferedReader(new InputStreamReader(System.in));

            String serverSays = "";
            String clientSays = "";
            while (!serverSays.equals("stop")) {
                clientSays = readFromClient.readUTF();
                System.out.println("Client says: " + clientSays);
                serverSays = enter.readLine();
                writeToClient.writeUTF(serverSays);
                writeToClient.flush();
            }

            readFromClient.close();
            server.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
