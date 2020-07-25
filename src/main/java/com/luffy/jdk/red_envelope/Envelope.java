package com.luffy.jdk.red_envelope;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by luffy on 2020/7/21
 */
public class Envelope {

    public static List<BigDecimal> calcAllocate(BigDecimal packetMoney, int number) {
        if (packetMoney.doubleValue() < number * 0.01) {
            return Collections.emptyList();
        }
        Random random = new Random();
        // 金钱，按分计算 10块等于 1000分
        int money = packetMoney.multiply(BigDecimal.valueOf(100)).intValue();
        // 随机数总额
        double count = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[number];
        // 每人获得钱数
        List<BigDecimal> packetList = new ArrayList<>(number);
        // 循环生成 number 个 大于1 小于等 number * 99 的数字
        for (int i = 0; i < arrRandom.length; i++) {
            int r = random.nextInt((number) * 99) + 1;
            // 随机数总和
            count += r;
            // 随机数
            arrRandom[i] = r;
        }

        System.out.println("总数:" + count);
        System.out.println("随机数列表:" + JSON.toJSONString(arrRandom));
        for (double v : arrRandom) {
            System.out.println("百分比:" + v / count);
        }
        // 计算每人拆红包获得金额
        int c = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 每人获得随机数相加 计算每人占百分比
            double x = arrRandom[i] / count;
            // 每人通过百分比获得金额
            int m = (int) Math.floor(x * money);
            // 如果获得 0 金额，则设置最小值 1分钱
            if (m == 0) {
                m = 1;
            }
            // 计算获得总额
            c += m;
            // 如果不是最后一个人则正常计算
            if (i < arrRandom.length - 1) {
                packetList.add(new BigDecimal(m).divide(new BigDecimal(100)));
            } else {
                // 如果是最后一个人，则把剩余的钱数给最后一个人
                packetList.add(new BigDecimal(money - c + m).divide(new BigDecimal(100)));
            }
        }
        // 随机打乱每人获得金额
        Collections.shuffle(packetList);
        return packetList;
    }

    public static void main(String[] args) {

            List<BigDecimal> bigDecimals = calcAllocate(new BigDecimal("100"), 1);
            BigDecimal sum = new BigDecimal("0");
            for (BigDecimal bigDecimal : bigDecimals) {
                sum = sum.add(bigDecimal);
            }
        System.out.println(sum);
        System.out.println(JSON.toJSONString(bigDecimals));

    }

    public static void main2(String[] args) {
        final int num = 3;
        Random random = new Random();
        IntStream.range(0,num)
                .forEach(i -> System.out.println(i + ":" +random.nextInt((num) * 99) + 1));
    }
}
