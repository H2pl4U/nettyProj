package myprotocalv2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端Handler处理类，负责往服务端写数据
 * Created by Liuwei on 2020/7/21 15:40
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ProtocalEntity protocalEntity = new ProtocalEntity();
        protocalEntity.setId(1);
        protocalEntity.setName("md_setreportdatatime");
        protocalEntity.setParam("paramValue:{paramValue}");
        protocalEntity.setVersion("v2.0");
        ctx.writeAndFlush(protocalEntity);
    }
}
