package socket.buffer;

import java.nio.ByteBuffer;

public class ByteBufferDemo {
    public static void main(String[] args) {
        // create buffer
        System.out.println("create buffer");
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printBuffer(buffer);

        //add data to buffer
        System.out.println("add data to buffer");
        String s = "h2pl4u";
        buffer.put(s.getBytes());
        printBuffer(buffer);

        //check read
        System.out.println("check read");
        buffer.flip();
        printBuffer(buffer);

        // read data
        System.out.println("read data");
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        printBuffer(buffer);

        //compact
        System.out.println("compact");
        buffer.compact();
        printBuffer(buffer);

        //clear
        System.out.println("clear");
        buffer.clear();
        printBuffer(buffer);
    }

    /**
     * print buffer info
     * @param buffer
     */
    private static void printBuffer(ByteBuffer buffer) {
        System.out.println("mark:" + buffer.mark());
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("capacity:" + buffer.capacity());
    }
}
