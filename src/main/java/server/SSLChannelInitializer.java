package server;

import handler.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Https Channel初始化器
 * Created by Liuwei on 2020/7/17 14:51
 */
public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslContext;

    public SSLChannelInitializer() {
        //SSL证书文件 暂无，需要通过云服务器申请
        String keyStoreFilePath = "/root/.ssl/test.pkcs12";
        //密码
        String keyStorePassword = "Password@123";

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(keyStoreFilePath), keyStorePassword.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            sslContext = SslContextBuilder.forServer(keyManagerFactory).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        pipeline
                .addLast(new SslHandler(sslEngine))
                .addLast("decoder", new HttpRequestDecoder())
                .addLast("encoder", new HttpResponseEncoder())
                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                .addLast("handler", new HttpServerHandler());
        ;
    }
}