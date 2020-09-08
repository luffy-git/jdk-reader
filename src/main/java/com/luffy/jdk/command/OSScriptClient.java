package com.luffy.jdk.command;

import com.luffy.jdk.command.enums.OS;
import com.luffy.jdk.command.util.ProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Optional;

/**
 *  OS 脚本客户端
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 16:09:28
 */
@Slf4j
public class OSScriptClient {

    /** 从系统属性中读取当前系统类型 */
    public static final String OS_NAME = "os.name";
    public static final String LINUX = "linux".toUpperCase();
    /** 系统脚本控制器 */
    private static final OSScriptHandler HANDLER;

    //TODO 在JVM初始化之后读取系统类型,初始化对应的 OS 客户端控制器
    static {
        // 防止多实例启动并发执行
        synchronized (OS_NAME){
            String machine = loadMachine();
            HANDLER = OS.build(machine).handler();
        }
    }

    /**
     *  加载机器类型配置
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-08 10:24:03
     * @return java.lang.String
     */
    private static String loadMachine() {
        // 获取系统类型
        String os = System.getProperty(OS_NAME);
        if (StringUtils.isBlank(os)) {
            throw new NullPointerException("未知的客户端实例");
        }
        String upper = os.toUpperCase();
        if (!upper.startsWith(LINUX)) {
            throw new IllegalArgumentException("不支持运行的的客户端实例: " + os);
        }
        //TODO 执行 Linux 命令行获取当前系统类型
        return getMachine();
    }

    /**
     *  获取系统脚本客户端控制器实例
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 16:22:36
     * @return com.luffy.jdk.command.OSScriptHandler
     */
    public static OSScriptHandler instance(){
        return HANDLER;
    }

    /**
     *  执行 Linux 命令
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-08 14:18:02
     * @return java.lang.String
     */
    public static String getMachine() {
        String machine;
        try {
            machine = ProcessUtil.getInputStreamAsString(ProcessUtil.exec("uname -m").getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("执行脚本获取机器设备信息异常:" + e.getMessage());
        }
        return Optional
                .of(machine)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .orElseThrow(() -> new IllegalArgumentException("获取机器设备类型错误"));
    }
}
