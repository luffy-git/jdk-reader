package com.luffy.jdk.neos.enums;

import java.util.Optional;

/**
 * Created by Luffy on 2020/9/9
 */
public enum Machine {
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
