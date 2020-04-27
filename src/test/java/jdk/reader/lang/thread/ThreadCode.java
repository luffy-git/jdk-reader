package jdk.reader.lang.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *  线程调试类
 * </p>
 * @author luffy
 * @since 2020-03-28 19:32:09
 */
@Slf4j
public class ThreadCode {

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
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        return  bs.substring(1);
    }

}
