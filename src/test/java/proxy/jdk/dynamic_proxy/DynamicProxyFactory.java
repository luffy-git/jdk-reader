package proxy.jdk.dynamic_proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * <p>
 *  动态代理之不实现接口，通过内部匿名类方式
 * </p>
 * @author luffy
 * @since 2020-04-27 16:29:08
 */
@Slf4j
public class DynamicProxyFactory<T> {

    private final T target;

    public DynamicProxyFactory(T target){
        this.target = target;
    }


    /**
     * <p>
     *  返回对目标对象(target)代理后的对象(proxy)
     * </p>
     * @author luffy
     * @since 2020-04-27 15:57:42
     * @return java.lang.Object
     */
    public T proxy() {
        // 将目标对象托管到  InvocationHandler
        InvocationHandler handler = (proxy, method, args) -> {

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
        };

        //noinspection unchecked
        return (T)Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 目标对象使用的类加载器
                target.getClass().getInterfaces(),   // 目标对象实现的所有接口
                handler                              // 执行代理对象方法时候触发
        );
    }
}
