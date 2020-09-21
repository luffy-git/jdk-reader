package com.luffy.jdk.aspectj.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  切面类
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-21 09:24:08
 */
@Aspect
public class LicenseAspectJ {

    private static final Logger log = LoggerFactory.getLogger(LicenseAspectJ.class);

    /**
     *  切面方法
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-21 09:26:00
     * @param point
     * @return java.lang.Object
     */
    @Around("@annotation(com.luffy.jdk.aspectj.annotation.License)")
    public Object authenticate(ProceedingJoinPoint point) throws Throwable {
        log.info("执行方法");
        return point.proceed();
    }
}
