package myprotocalv2;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * 编解码工具类
 * Created by Liuwei on 2020/7/21 9:10
 */
public class ByteObjConverter {

    /**
     * 字节转换成对象
     * @param bytes
     * @return
     */
    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bi.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                oi.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

}
