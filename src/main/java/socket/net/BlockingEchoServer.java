package socket.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingEchoServer {

    public static void main(String[] args) {
        int port = 8888;

        ServerSocket serverSocket = null;
        try {
            // server monitor
            serverSocket = new ServerSocket(port);
            System.out.println("start success, port:" + port);
        } catch (IOException ex) {
            System.out.println("start fail, port:" + port);
        }

        try (
                //create socket
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                //accept client msg
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // send msg
                out.println(inputLine);
                System.out.println(" server -> " + clientSocket.getRemoteSocketAddress() + ":" + inputLine);
            }
        } catch (IOException e) {
            System.out.println("server error:" + e.getMessage());
        }
    }
}
