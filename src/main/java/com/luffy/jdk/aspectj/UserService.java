package com.luffy.jdk.aspectj;

import com.luffy.jdk.aspectj.annotation.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  用户 Service
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-21 09:25:51
 */
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @License
    public String getUser(){
        log.info("查询用户信息");
        return "luffy";
    }
}
