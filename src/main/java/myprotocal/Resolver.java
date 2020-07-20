package myprotocal;

/**
 * Created by Liuwei on 2020/7/20 13:42
 */
public interface Resolver {
    public boolean support(Message message);
    public Message resolve(Message message);

}
