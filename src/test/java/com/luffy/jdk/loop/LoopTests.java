package com.luffy.jdk.loop;

import com.luffy.jdk.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  循环相关测试用例
 * </p>
 * @author luffy
 * @since 2020-04-27 18:21:05
 */
@Slf4j
public class LoopTests {

    /**
     * <p>
     *  跳出多重循环
     * </p>
     * @author luffy
     * @since 2020-03-31 16:48:01
     */
    @Test
    public void manyLoopBreak(){
        ok : for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                log.info("j:{}" ,j);
                if(j >= 5){
                    break ok;
                }
            }
            log.info("i:{}" ,i);
        }
    }


    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();


    @Test
    public void executor() throws InterruptedException {
        log.info("Start a scheduled task");
        EXECUTOR.scheduleWithFixedDelay(new TimerTask() {
            @Override
            public void run() {
                log.info("你好啊,现在是: {} ",DateUtil.formatLocalDateTimeToString(DateUtil.getCurrentLocalDateTime(), DateUtil.DATE_FMT_13));
            }
        }, 0L,1L, TimeUnit.SECONDS);

        TimeUnit.DAYS.sleep(20);
    }

    @Test
    public void testExec() throws InterruptedException {
        while (true){
            log.info("距离凌晨还有: {} 分钟", getInterval());
            TimeUnit.MINUTES.sleep(1L);
        }
    }

    private static long getInterval(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60;
    }
}
