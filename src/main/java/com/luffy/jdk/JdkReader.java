package com.luffy.jdk;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  JDK 源码阅读工程
 * </p>
 * @author luffy
 * @since 2020-04-27 17:58:23
 */
public class JdkReader {

    public static void main(String[] args) {
        String key = "JFKDLAS_FDJSKAJF_FDSAFASD";
        String key2 = "fdjsaklfjdasl_fdjajf_fdjafdsa";
        //StringTokenizer st = new StringTokenizer(key, "_");
        System.out.println(key.replaceAll("_", ".").toLowerCase(Locale.ROOT));

    }


    /**
     *  字典转 Map
     * @author Luffy [lizm@mingtech.cn]
     * @since 2021-02-05 17:40:06
     * @param dict 字典对象
     * @return java.util.Map<K,V>
     */
    public static <K,V> Map<K,V> dictionaryToMap(Dictionary<K, V> dict){

        if(Objects.isNull(dict)){
            return Collections.emptyMap();
        }


        return  Collections
                    .list(dict.keys())
                    .stream()
                    .collect(Collectors.toMap(Function.identity(), dict::get));
    }

}
