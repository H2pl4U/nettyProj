package rpc.provider;


import rpc.netty.NettyServer;

/**
 * 服务器
 * Created by Liuwei on 2020/7/20 14:12
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 8088);
    }
}
