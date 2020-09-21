package com.luffy.jdk.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  标记需要织入切面的方法
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-21 09:26:28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface License {
}
