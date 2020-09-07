package com.luffy.jdk.command.handler;

import com.luffy.jdk.command.OSScriptHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *  ARM 控制器
 * </p>
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 15:03:54
 */
public class ArmOSHandler implements OSScriptHandler {

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
        String command;
        if(StringUtils.isBlank(ifaceName)){
            command = "tp -o link show";
        }else{
            command = "ip link show dev " + ifaceName;
        }
        return command.split("\\s+");
    }
}
