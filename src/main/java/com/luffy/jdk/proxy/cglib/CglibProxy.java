package com.luffy.jdk.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <p>
 *  cglib 动态代理
 * </p>
 * @author luffy
 * @since 2020-04-27 17:46:09
 */
@Slf4j
public class CglibProxy<T> implements MethodInterceptor {

    private final T target;

    public CglibProxy(T target) {
        this.target = target;
    }

    public T proxy() {
        Enhancer enhancer = new Enhancer();
        // 设置目标类
        enhancer.setSuperclass(target.getClass());
        // 设置拦截对象
        enhancer.setCallback(this);
        // 创建实例并返回
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        // 获取当前执行的方法的方法名
        String methodName = method.getName();
        // 方法返回值
        Object result;
        if ("find".equals(methodName)) {
            // 不需要执行代理对象方法 直接执行
            result = proxy.invokeSuper(o, objects);
        } else {
            // 开启事务
            log.info("method: {} begin transaction ...", method.getName());
            try {
                // 执行目标对象方法
                result = proxy.invokeSuper(o, objects);
            } catch (Exception e) {
                // 回滚事务
                log.info("method: {} rollback transaction ,error: {} ...", method.getName(), e.getMessage());
                throw e;
            }

            // 提交事务
            log.info("method: {} commit transaction ...", method.getName());
        }
        return result;
    }
}
