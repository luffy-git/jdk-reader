package com.luffy.jdk.command.handler;

import com.luffy.jdk.command.OSScriptHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *  Ubuntu 控制器
 * </p>
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 15:05:11
 */
public class UbuntuOSHandler implements OSScriptHandler {

    /**
     * <p>
     *  获取网卡信息
     * </p>
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 15:47:14
     * @param ifaceName 网卡名称
     * @return java.lang.String[]
     */
    @Override
    public String[] showIpAddr(String ifaceName) {
        String command = "ip -o link show";
        if(StringUtils.isNotBlank(ifaceName)){
            command += " dev " + ifaceName;
        }
        return command.split("\\s+");
    }
}
