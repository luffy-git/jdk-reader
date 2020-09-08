package com.luffy.jdk.command;

import com.alibaba.fastjson.JSON;

/**
 * Created by Luffy on 2020/9/7
 */
public class Test {

    private static final OSScriptHandler HANDLER = OSScriptClient.instance();

    public static void main(String[] args) {
        String[] command = HANDLER.showIpAddr("eth0");
        System.out.println(JSON.toJSONString(command));
    }
}
