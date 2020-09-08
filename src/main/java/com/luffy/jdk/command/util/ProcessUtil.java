/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package com.luffy.jdk.command.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 命令行操作工具集
 * @author airmoer
 */
@Slf4j
public class ProcessUtil {

    private static final ExecutorService s_processExecutor = Executors.newSingleThreadExecutor();

    public static int doExec(String cmd) throws Exception {
        String[] cmd_arr = new String[] { "bash", "-c", cmd };
        SafeProcess proc = null;
        try {
            proc = ProcessUtil.exec(cmd_arr);
            if (proc.waitFor() != 0) {
                log.info("ERROR-proc:{}",proc.exitValue());
            }
            ArrayList<String> list = parse(proc);
            JSONObject obs = new JSONObject();
            obs.put("Return InFo", list);
            String pretty = JSON.toJSONString(obs, 
                    SerializerFeature.PrettyFormat, 
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat
            );
            log.info("pretty:{}",pretty);
            return proc.exitValue();
        } catch (Exception e) {
            throw e;
        } finally {
            if (proc != null) {
                ProcessUtil.destroy(proc);
            }
        }
    }
    
    public static SafeProcess exec(String command) throws IOException {
        // Use StringTokenizer since this is the method documented by Runtime
        StringTokenizer st = new StringTokenizer(command);
        int count = st.countTokens();
        String[] cmdArray = new String[count];

        for (int i = 0; i < count; i++) {
            cmdArray[i] = st.nextToken();
        }

        return exec(cmdArray);
    }

    public static SafeProcess exec(final String[] cmdarray) throws IOException {
        // Serialize process executions. One at a time so we can consume all streams.
        Future<SafeProcess> futureSafeProcess = s_processExecutor.submit(new Callable<SafeProcess>() {

            @Override
            public SafeProcess call() throws Exception {
                Thread.currentThread().setName("SafeProcessExecutor");
                SafeProcess safeProcess = new SafeProcess();
                safeProcess.exec(cmdarray);
                return safeProcess;
            }
        });

        try {
            return futureSafeProcess.get();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static ArrayList<String> parse(SafeProcess proc) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        try (InputStreamReader isr = new InputStreamReader(proc.getInputStream());
                BufferedReader br = new BufferedReader(isr)) {
            String buf;
            while ((buf = br.readLine()) != null) {
                list.add(buf);
            }
        }
        return list;
    }
    
    public static String getInputStreamAsString(InputStream stream) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            char[] cbuf = new char[1024];
            int len;
            while ((len = br.read(cbuf)) > 0) {
                sb.append(cbuf, 0, len);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return sb.toString();
    }
    
    /**
     * @deprecated The method does nothing
     */
    @Deprecated
    public static void close() {
        s_processExecutor.shutdown();
    }

    public static void destroy(SafeProcess proc) {
        proc.destroy();
    }
}
