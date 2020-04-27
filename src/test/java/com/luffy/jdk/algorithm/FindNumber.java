package com.luffy.jdk.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

@Slf4j
public class FindNumber {

    /**
     * <p>
     *  从 n 个不重复 有序数组中快速找到缺少的数字
     * </p>
     * @author luffy
     * @since 2020-04-27 18:01:34
     */
    @Test
    public void test() {
        List<Integer> nums = Arrays.asList(1, 2, 4, 5, 7, 9, 10);
        int len = 10;

        BitSet bitSet = new BitSet(len);
        for (Integer num : nums) {
            bitSet.set(num);

        }

        log.info("1-10中不存在的数字有{}个", len - bitSet.cardinality());

        for (int i = 1; i < len; i++) {
            if (!bitSet.get(i)) {
                log.info(i + "");
            }
        }
    }
}
