package com.luffy.jdk.command;

import com.alibaba.fastjson.JSON;
import com.luffy.jdk.command.util.ProcessUtil;

/**
 * Created by Luffy on 2020/9/7
 */
public class Test {

    private static final OSScriptHandler handler = OSScriptClient.instance();

    public static void main(String[] args) throws Exception {
        System.out.println(ProcessUtil.doExec("ll"));
        String[] command = handler.showIpAddr("");
        System.out.println(JSON.toJSONString(command));
    }
}
