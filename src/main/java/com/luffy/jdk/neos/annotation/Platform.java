package com.luffy.jdk.neos.annotation;

import com.luffy.jdk.neos.enums.Machine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  设备平台属性
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-10-13 14:29:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Platform {

    /** 标记机械类型 */
    Machine machine();
}
