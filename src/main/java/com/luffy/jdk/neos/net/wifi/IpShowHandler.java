package com.luffy.jdk.neos.net.wifi;

import com.luffy.jdk.neos.Script;
import com.luffy.jdk.neos.annotation.Handler;
import com.luffy.jdk.neos.annotation.Injection;

/**
 *  IP相关控制器
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-09 16:00:25
 */
@Handler
public abstract class IpShowHandler implements Script {

    @Injection
    private static IpShowHandler handler;

    /**
     *  获取控制器实例
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-09 16:00:37
     * @return com.luffy.jdk.neos.net.wifi.IpShowHandler
     */
    public static IpShowHandler instance() {
        return handler;
    }


}
