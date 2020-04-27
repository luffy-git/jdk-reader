package com.luffy.jdk.loop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
}
