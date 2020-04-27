package proxy.jdk.static_proxy;

import org.junit.jupiter.api.Test;
import proxy.common.UserMapper;

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
