package jdk.reader.util.concurrent.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTests {



    /**
     * <p>
     *  测试公平锁
     *  公平按顺序执行
     *  先进入锁,先执行
     * </p>
     * @author luffy
     * @since 2020-03-31 18:23:56
     */
    @Test
    public void testFairLock(){
        this.testReentrantLock(new ReentrantLock(true));
    }

    /**
     * <p>
     *  测试非公平锁
     *  不公平
     *  先进入的不一定先执行
     * </p>
     * @author luffy
     * @since 2020-03-31 18:24:10
     */
    @Test
    public void testNonFairLock(){
        this.testReentrantLock(new ReentrantLock(false));
    }

    /**
     * <p>
     *  锁测试
     * </p>
     * @author luffy
     * @since 2020-03-31 18:24:21
     * @param lock 锁对象
     */
    private void testReentrantLock(final ReentrantLock lock){
        // 缓存线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        int num = 10;
        for (int i = 0; i < num; i++) {
            executorService.execute(() -> {
                long id = Thread.currentThread().getId();
                log.info("id:{},进入银行大厅排队...",id);

                try {
                    // 获取锁
                    lock.lock();
                    log.info("id:{},取钱 ing...",id);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    log.info("id:{},离开银行...",id);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(),e);
                }finally {
                    lock.unlock();
                }


            });
        }
        try {
            // 给每个人预留一个执行空间,再停掉线程池.此处并不精准.
            executorService.awaitTermination((num + 1) * 1000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
        executorService.shutdownNow();
    }
}
