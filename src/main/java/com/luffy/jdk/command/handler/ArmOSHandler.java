package com.luffy.jdk.command.handler;

import com.luffy.jdk.command.OSScriptHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * ARM 控制器
 *
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 15:03:54
 */
public class ArmOSHandler implements OSScriptHandler {

    /**
     * 获取网卡信息
     *
     * @param ifaceName 网卡名称
     * @return java.lang.String[]
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 15:47:14
     */
    @Override
    public String[] showIpAddr(String ifaceName) {
        String command;
        if (StringUtils.isBlank(ifaceName)) {
            command = "tp -o link show";
        } else {
            command = "ip link show dev " + ifaceName;
        }
        return command.split("\\s+");
    }
}
