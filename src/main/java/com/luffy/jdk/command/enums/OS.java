package com.luffy.jdk.command.enums;

import com.luffy.jdk.command.OSScriptHandler;
import com.luffy.jdk.command.handler.ArmOSHandler;
import com.luffy.jdk.command.handler.UbuntuOSHandler;

import java.util.Optional;

/**
 *  硬件客户端
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-08 10:09:23
 */
public enum OS {

    /** ARM */
    ARMV7L(){
        @Override
        public OSScriptHandler handler() {
            return new ArmOSHandler();
        }
    },
    /** X86 */
    X86_64 {
        @Override
        public OSScriptHandler handler() {
            return new UbuntuOSHandler();
        }
    };

    /**
     *  获取对应机器类型的执行控制器
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-08 10:03:02
     * @return com.luffy.jdk.command.OSScriptHandler
     */
    public abstract OSScriptHandler handler();

    /**
     *  获取机器适配类型
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-08 10:00:52
     * @param machine 机器类型
     * @return com.luffy.jdk.command.enums.OS
     */
    public static OS build(String machine){
        OS os;
        try {
            os = valueOf(machine.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new NullPointerException("未知的机器类型:" + machine);
        }
        return Optional.of(os).orElseThrow(() -> new NullPointerException("未匹配的机器类型:" + machine));
    }
}
