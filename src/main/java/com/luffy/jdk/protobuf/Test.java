package com.luffy.jdk.protobuf;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Test {


    public static void main(String[] args) {

        Student student = new Student();
        student.setName("lance");
        student.setAge(28);
        student.setStudentNo("2011070122");
        student.setSchoolName("BJUT");

        log.info(JSON.toJSONString(student));
        byte[] serializerResult = ProtoBufUtil.serializer(student);

        log.info("serializer result:" + Arrays.toString(serializerResult));

        Student deSerializerResult = ProtoBufUtil.deserializer(serializerResult,Student.class);

        log.info("deSerializerResult:" + deSerializerResult.toString());
    }
}
