package rpc.common;

/**
 * Created by Liuwei on 2020/7/20 14:06
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return msg != null ? msg + " -----> I am fine." : "I am fine.";
    }
}
