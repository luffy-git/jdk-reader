package com.luffy.jdk.red_envelope;

import java.lang.management.ManagementFactory;

public class TestQuote {

    public static void main(String args[]) throws InterruptedException {

        // get name representing the running Java virtual machine.
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        // get pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
        Demo d1 = new Demo(1);
        Demo d2 = new Demo(2);
        System.out.println(d1.a);
        System.out.println(d2.a);
        System.out.println("d1-before:" + hex(d1) + " ;d2-before:" + hex(d2));
        function(d1, d2);
        System.out.println("d1-after:" + hex(d1) + " ;d2-after:" + hex(d2));
        System.out.println(d1.a);
        System.out.println(d2.a);
    }

    private static void function(Demo d1, Demo d2) {
        System.out.println("d1-in-fun:" + hex(d1) + " ;d2-in-fun:" + hex(d2));
        Demo temp;
        temp = d1;
        System.out.println("temp = d1 info:" + "temp:" + hex(temp) + " ;d1:" + hex(d1) + " ;d2:" + hex(d2));
        d1 = d2;
        System.out.println("d1 = d2 info:" + "temp:" + hex(temp) + " ;d1:" + hex(d1) + " ;d2:" + hex(d2));
        d2 = temp;
        System.out.println("d2 = temp info:" + "temp:" + hex(temp) + " ;d1:" + hex(d1) + " ;d2:" + hex(d2));
        d2 = null;
        d1 = null;
        System.out.println("d1 = null,d2 = null info:" + "temp:" + hex(temp) + " ;d1:" + hex(d1) + " ;d2:" + hex(d2));
    }

    private static String hex(Object x) {
        return Integer.toHexString(System.identityHashCode(x));
    }

    static class Demo {
        int a;

        public Demo(int a) {
            this.a = a;
        }
    }
}