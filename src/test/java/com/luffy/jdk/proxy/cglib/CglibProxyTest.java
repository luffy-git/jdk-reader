package com.luffy.jdk.proxy.cglib;

import com.luffy.jdk.proxy.common.UserDao;
import com.luffy.jdk.proxy.common.UserMapper;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *  基于CGLIB的代理测试，可以是接口 也可以是具体的实现类
 * </p>
 * @author luffy
 * @since 2020-04-27 17:37:04
 */
public class CglibProxyTest {

    @Test
    public void cglibTest1() {
        UserMapper mapper = new CglibProxy<>(new UserMapper()).proxy();
        mapper.delete("luffy");
    }

    @Test
    public void cglibTest2() {
        UserDao dao = new CglibProxy<>(new UserMapper()).proxy();
        dao.save("luffy");
    }
}
