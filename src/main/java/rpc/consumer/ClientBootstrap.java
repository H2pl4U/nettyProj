package rpc.consumer;

import rpc.common.HelloService;
import rpc.netty.NettyClient;

/**
 * Created by Liuwei on 2020/7/20 14:17
 */
public class ClientBootstrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {

        NettyClient consumer = new NettyClient();
        // 创建一个代理对象
        HelloService service = (HelloService) consumer.getBean(HelloService.class, providerName);

        for (; ; ) {
            Thread.sleep(1000);
            System.out.println(service.hello("are you ok ?"));
        }
    }

}
