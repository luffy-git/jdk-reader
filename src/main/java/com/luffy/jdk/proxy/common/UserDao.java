package com.luffy.jdk.proxy.common;

/**
 * <p>
 *  用户 DAO 接口
 * </p>
 * @author luffy
 * @since 2020-04-27 15:44:38
 */
public interface UserDao {

    int save(String user);

    int delete(String user);

    int update(String user);

    String query(String user);
}
