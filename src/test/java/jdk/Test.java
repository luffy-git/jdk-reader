package jdk;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

@Slf4j
public class Test {

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 4, 5, 7, 9, 10);
        int len = 10;

        BitSet bitSet = new BitSet(len);
        for (Integer num : nums) {
            bitSet.set(num);

        }

        log.info("1-10中不存在的数字有{}个", len - bitSet.cardinality());

        for (int i = 1; i < len; i++) {
            if (!bitSet.get(i)) {
                System.out.println(i);
            }
        }
    }
}
