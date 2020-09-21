package com.luffy.jdk.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  Hash工具類
 * </p>
 * @author luffy
 * @since 2019-08-06 14:54:32
 */
public final class HashUtil {

    private HashUtil(){}


    /**
     * <p>
     *  獲取Hash值
     * </p>
     * @author luffy
     * @since 2019-08-06 14:55:09
     * @param action 動作
     * @param detail 描述
     * @param time 多少秒内不重複
     * @return java.lang.String
     */
    public static String getHash(String action, String detail, int time) {
        return getHash(action, detail, TimeUnit.MINUTES, time);
    }

    /**
     * <p>
     *  生成一个两分钟内同一个用户永远不会发生改变的散列值
     * </p>
     * @author luffy
     * @since 2019-08-05 22:34:52
     * @param action 执行动作
     * @param detail 动作详情
     * @param timeUnit 時間格式
     * @param time 重複指數
     * @return java.lang.String
     */
    public static String getHash(String action, String detail, TimeUnit timeUnit, int time) {
        if (time <= 0) {
            time = 1;
        }
        if (null == timeUnit) {
            timeUnit = TimeUnit.SECONDS;
        }
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sbf = new StringBuilder(action);
        sbf.append("_").append(detail).append("_");
        sbf.append(now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_")));
        switch (timeUnit) {
            case DAYS:
                sbf.append((now.getDayOfMonth() / time));
                break;
            case HOURS:
                sbf.append((now.getHour() / time));
                break;
            case MINUTES:
                sbf.append((now.getMinute() / time));
                break;
            case SECONDS:
                sbf.append((now.getSecond() / time));
            default:
                sbf.append((now.getSecond() / time));
                break;
        }
        return sbf.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10000; i++) {
            try {
                System.out.println(getHash("CHECK_LOGIN:", "JWT:", TimeUnit.SECONDS, 5));
                // 1秒输出一次
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
