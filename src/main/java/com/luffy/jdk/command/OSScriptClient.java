package com.luffy.jdk.command;

import com.luffy.jdk.command.enums.OS;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *  OS 脚本客户端
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-07 16:09:28
 */
public class OSScriptClient {

    /** 从系统属性中读取当前系统类型 */
    public static final String OS_NAME = "os.name";
    public static final String LINUX = "linux".toUpperCase();
    /** 系统脚本控制器 */
    private static final OSScriptHandler HANDLER;

    //TODO 在JVM初始化之后读取系统类型,初始化对应的 OS 客户端控制器
    static {
        // 防止多实例启动并发执行
        synchronized (OS_NAME){
            String machine = loadMachine();
            HANDLER = OS.build(machine).handler();
        }
    }

    /**
     *  加载机器类型配置
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-08 10:24:03
     * @return java.lang.String
     */
    private static String loadMachine() {
        // 获取系统类型
        String os = System.getProperty(OS_NAME);
        if (StringUtils.isBlank(os)) {
            throw new NullPointerException("未知的客户端实例");
        }
        String upper = os.toUpperCase();
        if (!upper.startsWith(LINUX)) {
            throw new IllegalArgumentException("不支持运行的的客户端实例: " + os);
        }
        //TODO 执行 Linux 命令行获取当前系统类型
        return getMachine();
    }

    /**
     *  获取系统脚本客户端控制器实例
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-07 16:22:36
     * @return com.luffy.jdk.command.OSScriptHandler
     */
    public static OSScriptHandler instance(){
        return HANDLER;
    }

    private static String getMachine() {
        Process p = null;
        // git clone命令
        String cmd = "uname -m";
        try {
            // 起子进程执行cmd命令
            ProcessBuilder pb = new ProcessBuilder(cmd);
            p = pb.start();
            // 等待命令执行结束
            int exitValue = p.waitFor();
            // 创建readers， resReader用于读取标准输出，errReader用于读取错误输出
            BufferedReader resReader = new BufferedReader(new InputStreamReader((p.getInputStream())));
            BufferedReader errReader = new BufferedReader(new InputStreamReader((p.getErrorStream())));

            StringBuilder resStringBuilder = new StringBuilder();
            StringBuilder errStringBuilder = new StringBuilder();
            String line;
            while ((line = resReader.readLine()) != null) {
                resStringBuilder.append(line);
            }
            while ((line = errReader.readLine()) != null) {
                errStringBuilder.append(line);
            }

            // linux标准， exitValue为0时，表示执行正确结束
            // 当exitValue > 0时，抛出异常，并将获取的错误信息包装在Exception中
            if (exitValue > 0) {
                throw new RuntimeException(errStringBuilder.toString());
            }

            // 返回标准输出
            return resStringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 销毁子进程，释放资源
            if (p != null) {
                p.destroy();
            }
        }
    }
}
