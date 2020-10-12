package com.luffy.jdk.str;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;

/**
 *  字符串解析
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-10-12 17:42:12
 */
@Slf4j
public class StringParser {


    @Test
    public void testSplit(){
        log.info(parsePlugInName("neos.driver.modbus"));

    }

    private String parsePlugInName(String pack) {
        StringTokenizer tokenizer = new StringTokenizer(pack, ".");
        // 如果包名分级小于3级,则直接获取
        if (tokenizer.countTokens() < 3) {
            return pack;
        }
        // 分解包名，如果包层级大于等于３.则进行分割，取第三个子包名称为止
        int count = 3;
        StringBuilder sb = new StringBuilder();
        // 截取包名，截取３次
        while (count-- > 0) {
            sb.append(tokenizer.nextElement().toString()).append(".");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
