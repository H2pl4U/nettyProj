package live;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Liuwei on 2020/7/17 16:56
 */
public class LiveServer {

    private final int port;

    private static Logger logger = LoggerFactory.getLogger(LiveServer.class);

    public LiveServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        logger.debug("start server with port:" + 8086);
        new LiveServer(8086).start();
    }

    public void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        logger.debug("initChannel ch:" + ch);
                        ch.pipeline()
                                .addLast("decoder", new LiveDecoder())   // 1
                                .addLast("encoder", new LiveEncoder())  // 2
                                .addLast("aggregator", new HttpObjectAggregator(256 * 1024))    // 3
                                .addLast("handler", new LiveHandler());        // 4
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
        b.bind(port).sync();
    }
}
