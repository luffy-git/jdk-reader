package com.luffy.jdk.neos.annotation;

import com.luffy.jdk.neos.enums.Machine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Luffy on 2020/9/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Platform {

    Machine machine();
}
