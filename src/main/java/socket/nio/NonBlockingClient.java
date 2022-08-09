package socket.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingClient {
    public static void main(String[] args) {
        int port = 8888;
        String host = "127.0.0.1";
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));
        } catch (Exception ex) {
            System.err.println("client error:" + ex.getMessage());
            System.exit(1);
        }

        ByteBuffer writeBuffer = ByteBuffer.allocate(32);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                writeBuffer.put(userInput.getBytes());
                writeBuffer.flip();
                writeBuffer.rewind();

                //write
                socketChannel.write(writeBuffer);
                //read
                socketChannel.read(readBuffer);
                //clear
                writeBuffer.clear();
                readBuffer.clear();
                System.out.println("echo:" + userInput);
            }
        } catch (Exception ex) {
            System.out.println("client error:" + ex.getMessage());
        }
    }
}
