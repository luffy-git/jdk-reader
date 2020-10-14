package com.luffy.jdk.reader.lang.ref;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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



    List<String> list = new ArrayList<>();

    /**
     * <p>
     *  强引用
     *  软引用
     *  弱引用
     *  vm prop : -XX:+HeapDumpOnOutOfMemoryError -Xms3m -Xmx3m
     * </p>
     * @author luffy
     * @since 2020-03-31 15:43:06
     */
    @Test
    public void referenceTest() throws InterruptedException {
        log.info("time:");
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
        int i = 0;
       while (true){
           System.gc();

           TimeUnit.SECONDS.sleep(1);
            /*
            仅具有软引用的对象将仅在内存不足时被清除: 用来做缓存很不错
            gc操作后将清除仅具有弱引用的对象
         */
           log.info("After gc...");
           log.info("strongA = {}, softB = {}, weakC = {}", strongA, softB.get(), weakC.get());
           list.add( i++ + "房间地上咖啡机辣椒粉克拉斯荆防颗粒撒娇风口浪尖按时发快啦手机副卡拉设计费克拉斯荆防颗粒的煎熬是分开了的煎熬时空裂缝房间地上咖啡机辣椒粉克拉斯荆防颗粒撒娇风口浪尖按时发快啦手机副卡拉设计费克拉斯荆防颗粒的煎熬是分开了的煎熬时空裂缝");
       }


    }

    public String a(){
        return "l";
    }

}
