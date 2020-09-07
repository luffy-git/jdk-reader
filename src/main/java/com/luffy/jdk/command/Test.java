package com.luffy.jdk.command;

import com.alibaba.fastjson.JSON;

/**
 * Created by Luffy on 2020/9/7
 */
public class Test {

    private static final OSScriptHandler handler = OSScriptClient.instance();

    public static void main(String[] args) {
        String[] command = handler.showIpAddr("");
        System.out.println(JSON.toJSONString(command));
    }
}
