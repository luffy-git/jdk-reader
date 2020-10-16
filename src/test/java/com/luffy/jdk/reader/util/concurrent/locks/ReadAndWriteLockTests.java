package com.luffy.jdk.reader.util.concurrent.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * <p>
 *  读写锁测试
 * </p>
 * @author luffy
 * @since 2020-07-21 13:34:43
 */
@Slf4j
public class ReadAndWriteLockTests {

    /** 初始化的文件内容 */
    private static String $file = "文件";
    /** 读写锁初始化 */
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    @Test
    void testReadWriteLock() throws InterruptedException {
        // 初始化20个读写工作线程
        final int count = 20;
        // 初始化计数器
        final CountDownLatch latch = new CountDownLatch(count);
        // 初始化线程池
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        // 随机执行读操作和写操作
        IntStream.range(0,count).forEach(i -> executor.submit(new Random().nextBoolean() ? new Reader(latch) : new Writer(latch,i + "")));
        // 停止线程池
        executor.shutdown();
        // 等待所有任务执行完
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }


    /**
     * <p>
     *  获取当前线程ID
     * </p>
     * @author luffy
     * @since 2020-07-21 13:35:54
     */
    static long id(){
        return Thread.currentThread().getId();
    }

    /**
     * <p>
     *  读 工作线程
     * </p>
     * @author luffy
     * @since 2020-07-21 13:36:07
     */
    @Slf4j
    static class Reader implements Runnable{
        private final CountDownLatch latch;
        public Reader(CountDownLatch latch){
            this.latch = latch;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            this.latch.countDown();
            try {
                // 获取读锁
                LOCK.readLock().lock();
                log.info("读取文件内容,线程:{},文件内容:{}", id(), $file);
            } finally {
                // 释放读锁
                LOCK.readLock().unlock();
            }
        }
    }

    /**
     * <p>
     *  写 工作线程
     * </p>
     * @author luffy
     * @since 2020-07-21 13:36:21
     */
    @Slf4j
    static class Writer implements Runnable{
        private final CountDownLatch latch;
        /** 追加到文件中的内容 */
        private final String content;
        public Writer(CountDownLatch latch,String content){
            this.latch = latch;
            this.content = content;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            this.latch.countDown();
            try {
                // 获取写锁
                LOCK.writeLock().lock();
                // 模拟IO操作等待
                TimeUnit.SECONDS.sleep(1);
                String old = $file;
                // 修改文件内容
                $file += this.content;
                log.info("开始修改文件,线程:{},原始内容:{},修改后的内容:{}", id(), old, $file);
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
            } finally {
                // 释放写锁
                LOCK.writeLock().unlock();
            }
        }
    }
}
