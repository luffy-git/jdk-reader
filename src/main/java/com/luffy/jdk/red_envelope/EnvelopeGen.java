package com.luffy.jdk.red_envelope;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * <p>
 *  红包生成工具类
 * </p>
 * @author luffy
 * @since 2020-07-25 14:37:16
 */
public class EnvelopeGen {

    private static final Random R = new Random();

    public static List<BigDecimal> envelopes(BigDecimal money, int packet) {

        // 红包列表
        final List<BigDecimal> packetList = new ArrayList<>(packet);

        // 如果是单包
        if (1 == packet) {
            packetList.add(money);
            return packetList;
        }

        // 红包个数大于1，需要按比例生成
        final double[] percent = new double[packet];
        // 随机数总和
        long total = 0L;
        // 生成 n 个随机数,并获取计算 n 个随机数的总和
        for (int i = 0; i < packet; i++) {
            // 生成一个  num >= 1 and num <= n * 99
            int num = R.nextInt(packet * 99) + 1;
            // 随机生成的数字
            percent[i] = num;
            // 随机数累计求和
            total += num;

        }
        // 将红包总金额转为分
        long totalCent = money.multiply(BigDecimal.valueOf(100)).longValue();
        // 所有红包累计金额（分）
        long c = 0L;
        for (int i = 0; i < percent.length; i++) {
            // 计算每个 percent 在 total 中的占比
            double p = percent[i] / total;
            // 通过这个百分比获取总金额中当前比例的金额,并通过Math.floor去掉小数部分
            long m = (long) Math.floor(p * totalCent);

            // 如果当前比例金额为0,则让该包最少为1分钱
            if (0L == m) {
                m = 1L;
            }

            // 如果不是最后一个红包就将分配完的金额累加到 变量 c
            if(i < percent.length - 1){
                // 累计已经分配的金额
                c += m;
            }
            // 最后一个红包,将所有未分配的金额全部分配
            else{
                m = totalCent - c;
            }
            packetList.add(new BigDecimal(m).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN));
        }
        return packetList;
    }

    public static void main(String[] args) {
        final BigDecimal money = new BigDecimal(10000);
        final int packet = 10;
        IntStream.range(1,20)
                .forEach(i -> System.out.println(JSON.toJSONString(envelopes(money, packet))));
    }
}
