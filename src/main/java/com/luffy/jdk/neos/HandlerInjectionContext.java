package com.luffy.jdk.neos;

import com.luffy.jdk.neos.annotation.Handler;
import com.luffy.jdk.neos.annotation.Injection;
import com.luffy.jdk.neos.annotation.Platform;
import com.luffy.jdk.neos.enums.Machine;
import com.luffy.jdk.neos.net.wifi.IpShowHandler;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 *  控制器注入上下文
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-09 15:59:14
 */
@Slf4j
public final class HandlerInjectionContext {

    private static final String OS = "ARMV7L";

    private HandlerInjectionContext() {

    }

    // 初始化注入
    static {
        // 获取 pack 包下所有 @Handler 注解的类
        Reflections factoryRef = new Reflections(HandlerInjectionContext.class.getPackage().getName());
        Set<Class<?>> factory = factoryRef.getTypesAnnotatedWith(Handler.class, true);
        Machine instance = Machine.instance(OS);
        factory.forEach(f -> {
            // 找到 工厂包下所有的子类,除自身
            Reflections handlerRef = new Reflections(f.getPackage().getName());
            Set<Class<?>> handlers = handlerRef.getTypesAnnotatedWith(Platform.class, true);
            // 取得当前机器适配的执行对象
            handlers
                    .stream()
                    .filter(h -> h.getAnnotation(Platform.class).machine().equals(instance))
                    .findFirst()
                    .ifPresent(h -> injection(f, h));
        });
    }

    /**
     *  注入属性
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-09 15:59:45
     * @param clazz 属性类型
     * @param obj 属性值
     */
    private static void injection(Class<?> clazz, Class<?> obj) {
        Arrays
                .stream(clazz.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(Injection.class)))
                .findAny()
                .ifPresent(f -> setField(f, obj));
    }

    /**
     *  注入字段值
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 10:49:40
     * @param field 属性字段
     * @param obj 字段值
     * @throws RuntimeException 设置字段值异常
     */
    private static void setField(Field field, Class<?> obj){
        field.setAccessible(true);
        try {
            field.set(obj,obj.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("设置平台操作脚本控制器异常: " + e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(IpShowHandler.instance().command("-dd")));
        System.out.println(Arrays.toString(IpShowHandler.instance().command("-dd")));
        System.out.println(Arrays.toString(IpShowHandler.instance().command("-dd")));
        System.out.println(Arrays.toString(IpShowHandler.instance().command("-dd")));
    }
}
