package com.luffy.jdk.command;

import com.alibaba.fastjson.JSON;
import com.luffy.jdk.command.util.ProcessUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by Luffy on 2020/9/7
 */
@Slf4j
public class Test {

    private static final OSScriptHandler HANDLER = OSScriptClient.instance();

    public static void main(String[] args) throws IOException {
        String[] command = HANDLER.showIpAddr("eth0");
        log.info("命令:{},执行结果:{}", JSON.toJSONString(command), ProcessUtil.getInputStreamAsString(ProcessUtil.exec(command).getInputStream()));

    }
}
