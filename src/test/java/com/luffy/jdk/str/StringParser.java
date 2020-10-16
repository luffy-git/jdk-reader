package com.luffy.jdk.str;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

    private String parsePlugInName(final String pack) {
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

    @Test
    public void testClassForName() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName(Student.class.getName()).getConstructor(String.class, Integer.class).newInstance("lizhimin", 18);
    }

    @Test
    public void testFiled(){
        // String name = Reflect.on(Class.forName(Student.class.getName()).getConstructor(String.class, Integer.class).newInstance("lizhimin", 18)).field("name").get();
        long versionUID = Reflect.on(Student.class).create().field("serialVersionUID").get();
        log.info(versionUID + "");
        log.info((2 & 0xFFFF) + "");
    }

    @Slf4j
    @Getter
    @Setter
    public static class Student implements Serializable {

        private static final long serialVersionUID = -8416624024386607834L;
        private String name;
        private Integer age;

        public Student(){
            log.info("super");
        }

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
            log.info("name : {}, age : {}", name, age);
        }
    }
}
