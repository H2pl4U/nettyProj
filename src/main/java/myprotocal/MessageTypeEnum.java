package myprotocal;

/**
 * Created by Liuwei on 2020/7/20 11:56
 */
public enum MessageTypeEnum {
    REQUEST((byte) 1), RESPONSE((byte) 2), PING((byte) 3), PONG((byte) 4), EMPTY((byte) 5);

    private byte type;

    MessageTypeEnum(byte type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static MessageTypeEnum get(byte type) {
        for (MessageTypeEnum value : values()) {
            if (value.type == type) {
                return value;
            }
        }

        throw new RuntimeException("不支持类型: " + type);
    }
}
