package com.luffy.jdk.command;

import com.luffy.jdk.command.enums.OS;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *  OS 脚本客户端
 * </p>
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 16:09:28
 */
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
        String machine = "";
        return machine;
    }

    /**
     * <p>
     *  获取系统脚本客户端控制器实例
     * </p>
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 16:22:36
     * @return com.luffy.jdk.command.OSScriptHandler
     */
    public static OSScriptHandler instance(){
        return HANDLER;
    }
}
