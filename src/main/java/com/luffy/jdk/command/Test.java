package com.luffy.jdk.command;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Luffy on 2020/9/7
 */
@Slf4j
public class Test {

    //private static final OSScriptHandler HANDLER = OSScriptClient.instance();

    public static void main(String[] args) {
        String name = "lizhimin";
        System.out.println(StringUtils.rightPad(name,30,'0'));

        new Thread(){

        };

    }
}
