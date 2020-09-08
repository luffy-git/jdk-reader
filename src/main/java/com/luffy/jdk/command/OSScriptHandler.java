package com.luffy.jdk.command;


/**
 *  Operating System 命令控制器
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 15:03:33
 */
public interface OSScriptHandler {

    /**
     *  获取网卡信息
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 15:47:14
     * @param ifaceName 网卡名称
     * @return java.lang.String[]
     */
    String[] showIpAddr(String ifaceName);
}
