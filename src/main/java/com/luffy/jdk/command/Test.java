package com.luffy.jdk.command;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Luffy on 2020/9/7
 */
@Slf4j
public class Test {

    private static final OSScriptHandler HANDLER = OSScriptClient.instance();

    public static void main(String[] args) {
        String[] command = HANDLER.showIpAddr("eth0");
        log.info("需要执行的命令及参数:{}", JSON.toJSONString(command));

    }
}
