package myprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * Created by Liuwei on 2020/7/20 11:57
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) {
        // 这里会判断消息类型是不是EMPTY类型，如果是EMPTY类型，则表示当前消息不需要写入到管道中
        if (message.getMessageType() != MessageTypeEnum.EMPTY) {
            out.writeInt(message.getMagicNumber());    // 写入当前的魔数
            out.writeByte(message.getMainVersion());    // 写入当前的主版本号
            out.writeByte(message.getSubVersion());    // 写入当前的次版本号
            out.writeByte(message.getModifyVersion());    // 写入当前的修订版本号
            if (!message.getSessionId().equals("") || message.getSessionId() != null) {
                // 生成一个sessionId，并将其写入到字节序列中
                String sessionId = "20200720123001";
                message.setSessionId(sessionId);
                out.writeCharSequence(sessionId, Charset.defaultCharset());
            }

            out.writeByte(message.getMessageType().getType());    // 写入当前消息的类型
            out.writeShort(message.getAttachments().size());    // 写入当前消息的附加参数数量
            message.getAttachments().forEach((key, value) -> {
                Charset charset = Charset.defaultCharset();
                out.writeInt(key.length());    // 写入键的长度
                out.writeCharSequence(key, charset);    // 写入键数据
                out.writeInt(value.length());    // 希尔值的长度
                out.writeCharSequence(value, charset);    // 写入值数据
            });

            if (null == message.getBody()) {
                out.writeInt(0);    // 如果消息体为空，则写入0，表示消息体长度为0
            } else {
                out.writeInt(message.getBody().length());
                out.writeCharSequence(message.getBody(), Charset.defaultCharset());
            }
        }
    }
}
