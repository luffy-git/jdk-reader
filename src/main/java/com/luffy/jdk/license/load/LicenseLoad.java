package com.luffy.jdk.license.load;

/**
 *  授权码加载类
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-14 14:50:53
 */
public interface LicenseLoad {

    /**
     *  获取授权码
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 14:50:17
     * @return java.lang.String
     */
    String license();

    /**
     *  修改授权,由于当前版本硬编码处理,暂时可以不用处理该事件
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 14:50:01
     * @param license 新的授权码
     */
    default void update(String license){}
}
