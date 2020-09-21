package com.luffy.jdk.license;

import com.luffy.jdk.license.core.RSAUtil;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  授权控制器
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-14 16:44:30
 */
public final class LicenseHandler {

    private LicenseHandler(){}

    /**
     *  生成使用期限 单位为年
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 16:42:46
     * @param year 几年后过期
     * @return java.lang.String
     */
    private static String yearLater(int year){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.YEAR,year);
        return instance.getTimeInMillis() + "";
    }

    /**
     *  生成 license 字符串
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 16:43:04
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public static Map<String,String> genLicense(String publicKey,String privateKey){
        Map<String,String> prop = new HashMap<>(2);
        String content = yearLater(1);
        prop.put("license",RSAUtil.encryptedDataOnJava(content,publicKey));
        prop.put("sign", RSAUtil.sign(content.getBytes(StandardCharsets.UTF_8),privateKey));
        return prop;
    }

    /**
     *  授权验证
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 16:43:36
     * @param license 授权码
     * @param privateKey 私钥
     */
    private static void licenseVerify(String license,String privateKey){
        if(StringUtils.isBlank(license)  || StringUtils.isBlank(privateKey)){
            throw new IllegalArgumentException("");
        }

        // 解密
        String original = RSAUtil.decryptDataOnJava(license, privateKey);
        if(StringUtils.isBlank(original)){
            throw new IllegalArgumentException("");
        }

        // 验证 license 是否已过期
        long l = Long.parseLong(original) - System.currentTimeMillis();
        if(l <= 0){
            // license expired
            throw new IllegalArgumentException("");
        }
    }
}
