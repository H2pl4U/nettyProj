package myprotocalv2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端Handler处理类，主要是负责读取服务端接收到的数据直接打印
 * Created by Liuwei on 2020/7/21 15:46
 */
public class ProtocalHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtocalEntity protocalEntity = (ProtocalEntity) msg;
        System.out.println(protocalEntity.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
}
