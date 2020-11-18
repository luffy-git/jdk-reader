package com.luffy.jdk.reader.lang.thread;

import com.luffy.jdk.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 *  线程调试类
 * </p>
 * @author luffy
 * @since 2020-03-28 19:32:09
 */
@Slf4j
public class ThreadLocalTest {

    @Test
    public void threadOrder(){
        log.info("info");
        log.debug("debug");
        log.error("error");
        log.warn("warn");
        log.trace("trace");
    }
    /**
     * 将一个int数字转换为二进制的字符串形式。
     * @param num 需要转换的int类型数据
     * @param digits 要转换的二进制位数，位数不足则在前面补0
     * @return 二进制的字符串形式
     */
    public String toBinary(int num, int digits) {
        int value = 1 << digits | num;
        String bs = Integer.toBinaryString(value); //0x20 | 这是为了保证这个string长度是6位数
        return  bs.substring(1);
    }

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

    @Test
    public void dumpStack(){
        log.info(" start ");
        Thread.dumpStack();
        log.info(" end ");
    }

    private Thread t;

    @Test
    public void testThread() {
        while (true){
            new Thread(this::t).start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void t(){
        if(null != t){
            while (t.isAlive()){
                log.info("停止");
                t.interrupt();
            }
        }

        t = new Thread(() -> {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(DateUtil.getCurrentDateTime().toString());
                if (t.isInterrupted()){
                    log.info("停止");
                    t.interrupt();
                }
            }
        });
        t.start();
    }

    @Test
    public void testThreadPool() throws InterruptedException {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            System.out.println("222");
            try {
                Thread.currentThread().setName("luffy-");
                Thread.sleep(990);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 1, 1, TimeUnit.SECONDS);
        TimeUnit.MINUTES.sleep(60);
    }

}
