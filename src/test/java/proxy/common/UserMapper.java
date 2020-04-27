package proxy.common;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  用户 Mapper 类
 * </p>
 * @author luffy
 * @since 2020-04-27 15:48:05
 */
@Slf4j
public class UserMapper implements UserDao {

    @Override
    public int save(String user) {
        log.info("save user : {}",user);
        return 1;
    }

    @Override
    public int delete(String user) {
        log.info("delete user : {}",user);
        return 1;
    }

    @Override
    public int update(String user) {
        log.info("update user : {}",user);
        return 1;
    }

    @Override
    public String query(String user) {
        log.info("query user : {}",user);
        return "luffy";
    }
}
