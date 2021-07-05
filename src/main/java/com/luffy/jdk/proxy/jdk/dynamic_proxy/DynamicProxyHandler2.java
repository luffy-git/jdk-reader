package com.luffy.jdk.proxy.jdk.dynamic_proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *  动态代理之，通过实现 InvocationHandler 方法
 * </p>
 * @author luffy
 * @since 2020-04-27 16:29:26
 */
@Slf4j
public class DynamicProxyHandler2<T> implements InvocationHandler {

    private final Class<T> target;

    public DynamicProxyHandler2(Class<T> target){
        this.target = target;
    }

    public T proxy(){
        //noinspection unchecked
        return (T)Proxy.newProxyInstance(target.getClassLoader(), target.getInterfaces(), this);
    }


    /**
     * Processes a method invocation on a jdk.proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a jdk.proxy instance that it is
     * associated with.
     *
     * @param proxy  the jdk.proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the jdk.proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               jdk.proxy interface that the jdk.proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the jdk.proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return the value to return from the method invocation on the
     * jdk.proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the jdk.proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the jdk.proxy instance.
     * @see InvocationTargetException,IllegalAccessException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        // 获取当前执行的方法的方法名
        String methodName = method.getName();
        // 方法返回值
        Object result;
        if ("find".equals(methodName)) {
            // 不需要执行代理对象方法 直接执行
            result = method.invoke(target, args);
        } else {
            // 开启事务
            log.info("method: {} begin transaction ...", method.getName());
            try {
                // 执行目标对象方法
                result = method.invoke(target, args);
            } catch (Exception e) {
                // 回滚事务
                log.info("method: {} rollback transaction ,error: {} ...", method.getName(),e.getMessage());
                throw e;
            }

            // 提交事务
            log.info("method: {} commit transaction ...", method.getName());
        }
        return result;
    }
}
