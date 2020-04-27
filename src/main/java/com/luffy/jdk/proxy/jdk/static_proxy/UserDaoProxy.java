package com.luffy.jdk.proxy.jdk.static_proxy;

import com.luffy.jdk.proxy.common.UserDao;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  静态硬编码 代理模式
 * </p>
 * @author luffy
 * @since 2020-04-27 17:46:35
 */
@Slf4j
public class UserDaoProxy implements UserDao {

    private final UserDao dao;

    public UserDaoProxy (UserDao dao){
        this.dao = dao;
    }

    @Override
    public int save(String user) {
        // 开启事务
        log.info("method: save begin transaction ...");
        int r = -1;
        try {
            r = this.dao.save(user);
        } catch (Exception e) {
            log.info("method: save rollback transaction ,error: {} ...", e.getMessage());
        }
        log.info("method: save commit transaction ...");
        return r;
    }

    @Override
    public int delete(String user) {
        // 开启事务
        log.info("method: delete begin transaction ...");
        int r = -1;
        try {
            r = this.dao.delete(user);
        } catch (Exception e) {
            log.info("method: delete rollback transaction ,error: {} ...", e.getMessage());
        }
        log.info("method: delete commit transaction ...");
        return r;
    }

    @Override
    public int update(String user) {
        // 开启事务
        log.info("method: update begin transaction ...");
        int r = -1;
        try {
            r = this.dao.update(user);
        } catch (Exception e) {
            log.info("method: update rollback transaction ,error: {} ...", e.getMessage());
        }
        log.info("method: update commit transaction ...");
        return r;
    }

    @Override
    public String query(String user) {
        return this.dao.query(user);
    }
}
