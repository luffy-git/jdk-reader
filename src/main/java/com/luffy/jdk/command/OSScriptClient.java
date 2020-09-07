package com.luffy.jdk.command;

import com.luffy.jdk.command.handler.ArmOSHandler;
import com.luffy.jdk.command.handler.UbuntuOSHandler;

/**
 * <p>
 *  OS 脚本客户端
 * </p>
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 16:09:28
 */
public class OSScriptClient {

    /** 从系统属性中读取当前系统类型 */
    public static final String OS = "os.name";
    /** 系统脚本控制器 */
    private static final OSScriptHandler handler;

    //TODO 在JVM初始化之后读取系统类型,初始化对应的 OS 客户端控制器
    static {
        // 防止多实例启动并发执行
        synchronized (OS){
            // 获取系统类型
            String os = System.getProperty(OS);
            if(os.startsWith("arm")){
                handler = new ArmOSHandler();
            }else if(os.startsWith("ubuntu")){
                handler = new UbuntuOSHandler();
            }else{
                throw new IllegalArgumentException("未实现的客户端实例: " + os);
            }
        }
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
        return handler;
    }
}
