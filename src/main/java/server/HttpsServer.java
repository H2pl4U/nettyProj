package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Https请求
 * Created by Liuwei on 2020/7/17 14:52
 */
public class HttpsServer {
    private final int port;

    public HttpsServer(int port) {
        this.port = port;
    }

    /**
     * Https 服务器 base netty (没有SSL证书，启动报错)
     *
     * @param
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        HttpsServer httpsServer = new HttpsServer(87);
//        httpsServer.start();
//    }
    public void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SSLChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }
}
