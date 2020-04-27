package jdk.reader.lang.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class ThreadLocalTest {


    @Test
    public void test01(){
        ThreadLocal<String> local = new ThreadLocal<>();
        Random random = new Random();
        IntStream.range(0,5).forEach(t -> new Thread(() -> {
            local.set(t + " " + random.nextInt(10));
            log.info("id:" + Thread.currentThread().getId() + "  随机值为:" + local.get());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

    private static final ThreadLocal<Integer> TL_INT = ThreadLocal.withInitial(() -> 6);
    private static final ThreadLocal<String> TL_STRING = ThreadLocal.withInitial(() -> "Hello, world");

    /**
     * <p>
     *  使用ThreadLocal.withInitial初始化变量
     * </p>
     * @author luffy
     * @since 2020-03-31 15:42:50
     */
    @Test
    public void withInitial() {
        // 6
        log.info(TL_INT.get().toString());
        TL_INT.set(TL_INT.get() + 1);
        // 7
        log.info(TL_INT.get().toString());
        TL_INT.remove();
        // 会重新初始化该value，6
        log.info(TL_INT.get().toString());
    }

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
    public void reference(){
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
