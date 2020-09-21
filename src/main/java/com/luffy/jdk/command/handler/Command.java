package com.luffy.jdk.command.handler;

import java.io.ByteArrayOutputStream;

/**
 * Created by Luffy on 2020/9/8
 */
public interface Command<T,K> {

    String command();

    default T result(K k){
        return null;
    }



    class ArmIp implements Command<String[], ByteArrayOutputStream>{

        @Override
        public String command() {
            return "";
        }
    }

    class CentosIp implements Command<String[],ByteArrayOutputStream>{

        @Override
        public String command() {
            return "";
        }

        @Override
        public String[] result(ByteArrayOutputStream stream) {
            return new String[0];
        }
    }
}
