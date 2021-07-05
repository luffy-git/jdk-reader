package com.luffy.jdk.locale.container;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 *  国际化容器 java.util.Locale
 * @author Luffy [lizm@mingtech.cn]
 * @since 2021-05-12 16:34:19
 */
public final class MessageContainer {


    /** 缓存容器 */
    private static Map<String,String> CONTAINER;
    /** 容器实例 */
    private static MessageContainer instance;

    /**
     *  保护构造方法
     * @author Luffy [lizm@mingtech.cn]
     * @since 2021-05-12 17:09:46
     */
    private MessageContainer(){
        CONTAINER = new HashMap<>();
    }

    /**
     *  获取容器实例
     * @author Luffy [lizm@mingtech.cn]
     * @since 2021-05-12 17:09:59
     * @return com.luffy.jdk.locale.container.MessageContainer
     */
    public static MessageContainer getInstance(){
        if(Objects.isNull(instance)){
            synchronized (MessageContainer.class){
                if(Objects.isNull(instance)){
                    instance = new MessageContainer();
                }
            }
        }

        return instance;
    }

    public void put(){

    }

    /**
     *  从容器中获取国际化内容
     * @author Luffy [lizm@mingtech.cn]
     * @since 2021-05-12 17:14:05
     * @param pid Bundle PID
     * @param key 国际化 Key
     * @return java.lang.String
     */
    public String get(String pid,String key){
        return "";
    }

    /**
     *  从容器中获取国际化内容
     * @author Luffy [lizm@mingtech.cn]
     * @since 2021-05-12 17:14:05
     * @param pid Bundle PID
     * @param locale 本地化语言
     * @param key 国际化 Key
     * @return java.lang.String
     */
    public String get(String pid, Locale locale,String key){
        return "";
    }

    public void remove(String pid){
        CONTAINER.remove(pid);
    }
}
