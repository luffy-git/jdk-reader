package com.luffy.jdk.neos.enums;

import java.util.Optional;

/**
 *  设备型号
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-10-12 18:08:57
 */
public enum Machine {

    /** 支持的设备型号 */
    ARMV7L,
    X86_64
    ;

    public static Machine instance(String machine){
        Machine m;
        try {
            m = valueOf(machine.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new NullPointerException("未知的机器类型:" + machine);
        }
        return Optional.of(m).orElseThrow(() -> new NullPointerException("未匹配的机器类型:" + machine));
    }
}
