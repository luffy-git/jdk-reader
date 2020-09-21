package com.luffy.jdk.neos;

public interface Script {

    String[] command(String... param);

    default String[] result(){
        return null;
    }
}
