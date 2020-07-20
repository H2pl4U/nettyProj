package client;

import handler.HttpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Http 客户端 base 客户端
 * Created by Liuwei on 2020/7/20 11:28
 */
public class HttpClient {
    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8078;

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new HttpClientHandler());
                        }
                    });
            //启动客户端
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
