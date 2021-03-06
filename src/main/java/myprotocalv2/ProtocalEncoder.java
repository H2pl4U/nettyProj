package myprotocalv2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * Created by Liuwei on 2020/7/21 9:06
 */
public class ProtocalEncoder extends MessageToByteEncoder<ProtocalEntity> {
    @Override
    protected void encode(ChannelHandlerContext context, ProtocalEntity msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.ObjectToByte(msg);
        out.writeBytes(datas);
        context.flush();
    }
}
