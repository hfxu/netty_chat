import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class SocketClientExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8090);
        SocketChannel client = SocketChannel.open(hostAddress);
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);

        System.out.println("Client... started");

        Scanner scanner = new Scanner(System.in);

        Runnable runnable = () -> {
            int numRead = 0;

            try {
                while (true) {
                    int num = selector.select();
                    if (num == 0) continue;

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        System.out.println(key.isReadable());
                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            sc.read(buffer);
                            byte[] data = new byte[numRead];
                            System.arraycopy(buffer.array(), 0, data, 0, numRead);
                            System.out.println("Got: " + new String(data));

                        }
                        key.interestOps(SelectionKey.OP_READ);

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        };

        new Thread(runnable).start();

        while (true) {
            System.out.println("请输入消息并按Enter键发送（关闭请输入bye）：");
            String m = scanner.nextLine();
            if (m.trim().toLowerCase().equals("bye")) {
                break;
            }
            byte[] message = m.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);
            buffer.clear();
        }
        client.close();
    }
}