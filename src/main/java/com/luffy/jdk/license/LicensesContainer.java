package com.luffy.jdk.license;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;
import java.util.Properties;

/**
 *  授权码容器类
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-15 09:05:43
 */
public final class LicensesContainer {

    private LicensesContainer(){}

    /** Licenses 容器 */
    private static final Properties LICENSES = new Properties();

    /**
     *  获取某个插件的配置属性
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-15 09:07:19
     * @param pack 插件名称
     * @param flag true:授权码,false:私钥
     * @return java.lang.String
     */
    public static String getProperties(String pack, boolean flag) {
        if (Objects.isNull(pack) || pack.isEmpty()) {
            throw new NullPointerException();
        }
        if (LICENSES.isEmpty()) {
            // load properties need sync
            synchronized (LicensesContainer.class) {
                // load properties by file
                initLicenses();
            }
        }

        String key = flag ? pack + ".license" : pack + ".private-key";
        return LICENSES.getProperty(key);
    }

    /**
     *  获取插件License
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-15 09:35:05
     * @param pack 插件名称
     * @return java.lang.String
     */
    public static String getLicense(String pack){
        return getProperties(pack,true);
    }

    /**
     *  获取插件秘钥
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-15 09:35:05
     * @param pack 插件名称
     * @return java.lang.String
     */
    public static String getPrivateKey(String pack){
        return getProperties(pack,false);
    }

    /**
     *  初始化,从文件中读取授权码
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-15 09:07:45
     */
    private static synchronized void initLicenses(){
        try {
            String licenseProperties = "";
            LICENSES.load(new StringReader(licenseProperties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public static void main(String[] args) {
        System.out.println(LicensesContainer.class.getName());
    }

    /**
     *  授权过期
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-15 11:02:07
     */
    public static class LicenseExpired {

        private static IllegalArgumentException LICENSE_EXPIRED;

        /**
         *  授权过期
         * @author Luffy [lizm@mingtech.cn]
         * @since 2020-09-15 11:02:16
         */
        public static void expired() {
            if(Objects.isNull(LICENSE_EXPIRED)) {
                LICENSE_EXPIRED = new IllegalArgumentException("License has expired");
            }
            throw LICENSE_EXPIRED;
        }



    }
}
