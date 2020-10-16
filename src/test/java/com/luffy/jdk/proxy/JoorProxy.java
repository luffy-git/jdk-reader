package com.luffy.jdk.proxy;

import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;
import org.junit.jupiter.api.Test;

/**
 *  Joor 代理模式
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-10-16 11:59:51
 */
@Slf4j
public class JoorProxy {

    @Test
    public void proxy() {

        String substring = Reflect.on("java.lang.String")
                .create("Hello World")
                .as(StringProxy.class) // 为包装类建立一个代理
                .substring(6);         // 访问代理方法
        log.info(substring);

    }

    public interface StringProxy {
        String substring(int beginIndex);
    }

}
