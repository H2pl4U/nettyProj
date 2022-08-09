package socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlokingServer {
    public static void main(String[] args) {
        int port = 8888;
        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(port);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start, port: " + port);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (Exception ex) {
                System.out.println("server error! msg:" + ex.getMessage());
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                try {
                    // isAcceptable
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = server.accept();
                        System.out.println("server accept connection:" + socketChannel);

                        //set nonBlocking
                        socketChannel.configureBlocking(false);

                        //client register to selector
                        SelectionKey clientKey = socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);

                        //allocate buffer
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }

                    // isReadable
                    if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer output = (ByteBuffer) selectionKey.attachment();
                        client.read(output);
                        System.out.println(client.getRemoteAddress() + "-> server:" + output.toString());
                        selectionKey.interestOps(SelectionKey.OP_WRITE);
                    }

                    //isWritable
                    if (selectionKey.isWritable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer output = (ByteBuffer) selectionKey.attachment();
                        output.flip();
                        client.write(output);
                        System.out.println("server -> " + client.getRemoteAddress() + ":" + output.toString());
                        output.compact();
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                } catch (Exception ex) {
                    selectionKey.cancel();
                    try {
                        selectionKey.channel().close();
                    } catch (IOException e) {
                        System.out.println("server error, msg:" + e.getMessage());
                    }
                }
            }
        }
    }
}
