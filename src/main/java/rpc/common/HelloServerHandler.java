package rpc.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.consumer.ClientBootstrap;
import rpc.provider.HelloServiceImpl;

/**
 * Created by Liuwei on 2020/7/20 14:11
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // 如何符合约定，则调用本地方法，返回数据
        if (msg.toString().startsWith(ClientBootstrap.providerName)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }
}
