package com.luffy.jdk.protobuf;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * Created by luffy on 2020/7/19
 */
public class Test {

    public static void main(String[] args) {

        Student student = new Student();
        student.setName("lance");
        student.setAge(28);
        student.setStudentNo("2011070122");
        student.setSchoolName("BJUT");

        System.out.println(JSON.toJSONString(student));
        byte[] serializerResult = ProtoBufUtil.serializer(student);

        System.out.println("serializer result:" + Arrays.toString(serializerResult));

        Student deSerializerResult = ProtoBufUtil.deserializer(serializerResult,Student.class);

        System.out.println("deSerializerResult:" + deSerializerResult.toString());
    }
}
