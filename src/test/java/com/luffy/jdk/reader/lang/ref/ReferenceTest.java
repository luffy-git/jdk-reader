package com.luffy.jdk.reader.lang.ref;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * <p>
 *  引用类型测试类
 *  强引用
 *  软引用
 *  弱引用
 *  虚引用
 * </p>
 * @author luffy
 * @since 2020-04-27 18:23:30
 */
@Slf4j
public class ReferenceTest {




    /**
     * <p>
     *  强引用
     *  软引用
     *  弱引用
     * </p>
     * @author luffy
     * @since 2020-03-31 15:43:06
     */
    @Test
    public void referenceTest(){
        // 强引用
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        Object strongA = a;
        // 软引用
        SoftReference<Object> softB = new SoftReference<>(b);
        // 弱引用
        WeakReference<Object> weakC = new WeakReference<>(c);

        // 释放以前对这些对象的强引用

        // 强引用（strongA）
        a = null;
        // 仅 软引用（softB）引用第二个对象
        b = null;
        // 仅 弱引用（weakC）引用第三个对象
        c = null;

        log.info("Before gc...");
        log.info("strongA = {}, softB = {}, weakC = {}", strongA, softB.get(), weakC.get());

        log.info("Run GC...");

        System.gc();

        /*
            仅具有软引用的对象将仅在内存不足时被清除: 用来做缓存很不错
            gc操作后将清除仅具有弱引用的对象
         */
        log.info("After gc...");
        log.info("strongA = {}, softB = {}, weakC = {}", strongA, softB.get(), weakC.get());
    }

}
