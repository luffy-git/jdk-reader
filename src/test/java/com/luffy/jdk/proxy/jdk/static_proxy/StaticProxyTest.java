package com.luffy.jdk.proxy.jdk.static_proxy;

import com.luffy.jdk.proxy.common.UserMapper;
import com.luffy.jdk.proxy.jdk.static_proxy.UserDaoProxy;
import org.junit.jupiter.api.Test;


/**
 * <p>
 *  测试静态代理
 * </p>
 * @author luffy
 * @since 2020-04-27 16:36:32
 */
public class StaticProxyTest {

    @Test
    public void staticProxy(){
        UserDaoProxy dao = new UserDaoProxy(new UserMapper());
        dao.save("luffy");
    }

}
