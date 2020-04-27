package proxy.jdk.dynamic_proxy;

import org.junit.jupiter.api.Test;
import proxy.common.UserDao;
import proxy.common.UserMapper;

/**
 * <p>
 *  测试动态代理
 * </p>
 * @author luffy
 * @since 2020-04-27 16:25:17
 */
public class DynamicProxyTest {



    @Test
    public void dynamicProxy1(){
        /*
            UserMapper dao = new DynamicProxyHandler<>(new UserMapper()).proxy();
            使用jdk生成的动态代理的前提是目标类必须有实现的接口
            代理接口对象.不能直接使用目标对象获取
         */
        UserDao dao = new DynamicProxyHandler<>(new UserMapper()).proxy();
        dao.delete("luffy");
    }

    @Test
    public void dynamicProxy2(){
        /*
            UserMapper dao = new DynamicProxyHandler<>(new UserMapper()).proxy();
            使用jdk生成的动态代理的前提是目标类必须有实现的接口
            代理接口对象.不能直接使用目标对象获取
         */
        UserDao dao = new DynamicProxyFactory<UserDao>(new UserMapper()).proxy();
        dao.save("luffy");
    }
}
