package com.luffy.jdk.license.core;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  使用基于RSA的可逆算法实现的授权认证类
 * @author Luffy [lizm@mingtech.cn]
 * @since 2020-09-14 11:37:01
 */
public final class RSAUtil {

    private RSAUtil(){

    }

    /** 加密算法RSA */
    public static final String KEY_ALGORITHM = "RSA";
    /** 签名算法 */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 1.获取公钥的key
     * 2.获取私钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey",PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 1.RSA最大加密明文大小
     * 2.RSA最大解密密文大小
     */
    private static final int ENCRYPT_BLOCK = 245, DECRYPT_BLOCK = ENCRYPT_BLOCK + 11;
    /**
     * RSA 位数 如果采用1024 上面最大加密和最大解密则须填写:  117 128
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 2048;


    /**
     *  生成密钥对(公钥和私钥)
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:40:03
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(INITIALIZE_LENGTH,new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
        return keyMap;
    }


    /**
     *  用私钥对信息生成数字签名
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:41:34
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return java.lang.String
     */
    public static String sign(byte[] data, String privateKey) {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateK);
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *  校验数字签名
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:42:05
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * @return boolean true:通过,false:未通过
     */
    public static boolean verify(byte[] data, String publicKey, String sign){
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *  私钥解密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:42:39
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return byte[]
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int inputLen = encryptedData.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * DECRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  公钥解密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:50:49
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return byte[]
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            int inputLen = encryptedData.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * DECRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  公钥加密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:51:10
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return byte[]
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey){
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * ENCRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  私钥加密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:51:38
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return byte[]
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateK);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * ENCRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  获取私钥
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:51:57
     * @param keyMap 公钥/私钥
     * @return java.lang.String
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     *  获取公钥
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:51:57
     * @param keyMap 公钥/私钥
     * @return java.lang.String
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     *  java端公钥加密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:53:14
     * @param data 元数据
     * @param publicKey 私钥
     * @return java.lang.String
     */
    public static String encryptedDataOnJava(String data, String publicKey) {
        return Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), publicKey));
    }

    /**
     *  java端私钥解密
     * @author Luffy [lizm@mingtech.cn]
     * @since 2020-09-14 11:53:14
     * @param data 元数据
     * @param privateKey 公钥
     * @return java.lang.String
     */
    public static String decryptDataOnJava(String data, String privateKey) {
        byte[] rs = Base64.decodeBase64(data);
        return new String(RSAUtil.decryptByPrivateKey(rs, privateKey), StandardCharsets.UTF_8);
    }

    public static final String PRI_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDqFGmP3rAMNFTdSgAm2C/CG8anU7yV/dyLy6pyAAfCnV+PDuE/WVM5Yjz36J211Fw6jL3jaw7Q97SLij6N2E5C8FajvyY4jFkX4WjCaZcmo4PE+ba+GM5AtcnTG3NihltIALQwU1bAKoZQHGPunCPjGDX/1Y5mlSsuX/0nqiNfMJlGqG+f7f5PExqtlF0+XerDYb2nD5NH/sNoU+UY5FJMv5Jx4NRxGcvI2MM0AdZvA/wFWgMqZ3yaVNKNDS+yRKQ1ILuMJJ5pBEcdX4nNmFcG8ieThttGH1cWKaGTujVIOd3HVtPCXS/5vPwxa+3UlIEpn7+ZV9KMbVoUHpi7JSsVAgMBAAECggEAf5IyWcNOhMg8fAGphYIKvSowIN9FByW8fb+4m7qigBEOlEPSxJik/8AnZCC1WvRqc0pi7r9Y6tAk6GumXd3GATtErKcvaGAs6hPh6Rps0xUIamCBlKamOaQZY2ExiD+bHemsSi4ROFe0rZJbcB5OfKSkQVKc8hFX4EVWDbju4B/sSMSDYYzpWpkNfkjxMwUDU7Z6Z/PfhVqaJQCUbbZb6yroY3kvwIx05vq5Ok6jb8QV7ZIhIunI6Z+90Vxpe3wjKptPBaz2++CHrkq4GdgnSKheRIxfrWbVLNHH4cHKsjm9pr++97YD4pbZs+NIJqumdb/Zf9crWt4EM7tB7AJt4QKBgQD/XVfBLgVL/cgxw3wZjpIdNEQqrsEZHjGQSDmwdpZObc52+rZMLFPQMhm0AB7khJiqQDDAS456NptkQNFdsWhsNQrIdUke3Yqtar2vKXrfvenNbrHo9bVdNf6c7tANuTxoa7Whcvg42AvqDgSnUgUwio2e71ySt+xDpyxLIM3ryQKBgQDqqYMNoNi2vHjJvaHGlhIR+XBfJ0BamnVwFIznPrFm1dckjRSBTcRcFMgTFlQxTxbswSd22xItnua9hq7AaHJv0K1B2Gv7cyNHWieAttIBjdQPFGgV6p48rcf5JMx6XweNFCVSzAPbPRvtqGcf6AuVVTjormRNI8m42zM+Jd/S7QKBgGxSlB8F/ok1pe42FjZhG+n3edMBPjgBbtTdTltkcm3idmpR/3Jge00dc3m/c1tMQ9Y0VHm9kzqUX2YveKBd2QXNmj1eQx4sq97UIJJk7hJq5PqXsjA3yi838EFxrB+mK9G/ntRViKXUP3mRaLfxOvJUU67Pql0yV657A3b6+17BAoGAYWn5M4EgNcvLwMPuEohd/AQ2t+mNd49DTUHatGx8LJp883l1l6/24drmq7XLQaT5eVM91MNgqnuagfcVOF6jkvNn2TvEp9/GN2qfl3lNeMOt0ozAIMkC42Go8C5sjChC9Df2voZe23Zkz6XnGYG0vUthkoZCsRs4NHsarKQAZIkCgYAYW3xBysNRJRSBQexFnobV3N4ZpWUHLyCdI7iRYVmIBZR5qC06AxK4WJMUZ90S68cyQqQzIcyM6+z8Zseg6xni9t7QsURr1feuZUWo7hOIsmdKvsj9scaY4SeKVv6ZOfsOfh8DYTT+Js26vRWgDbovWJi+9GRLxfII5YSk9sYXjQ==";
    public static final String PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6hRpj96wDDRU3UoAJtgvwhvGp1O8lf3ci8uqcgAHwp1fjw7hP1lTOWI89+idtdRcOoy942sO0Pe0i4o+jdhOQvBWo78mOIxZF+FowmmXJqODxPm2vhjOQLXJ0xtzYoZbSAC0MFNWwCqGUBxj7pwj4xg1/9WOZpUrLl/9J6ojXzCZRqhvn+3+TxMarZRdPl3qw2G9pw+TR/7DaFPlGORSTL+SceDUcRnLyNjDNAHWbwP8BVoDKmd8mlTSjQ0vskSkNSC7jCSeaQRHHV+JzZhXBvInk4bbRh9XFimhk7o1SDndx1bTwl0v+bz8MWvt1JSBKZ+/mVfSjG1aFB6YuyUrFQIDAQAB";
    public static void main(String[] args) throws UnsupportedEncodingException {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.YEAR,1);
        String str = instance.getTimeInMillis() + "";
        System.out.println("当前时间:" + System.currentTimeMillis() + ";加密前:" + str);
        String encode = encryptedDataOnJava(str, PUB_KEY);
        String sign = sign(str.getBytes(StandardCharsets.UTF_8),PRI_KEY);
        System.out.println("签名:" + sign);
        System.out.println("验签:" + verify(str.getBytes(StandardCharsets.UTF_8),PUB_KEY, sign));
        System.out.println("加密后:" + encode);
        System.out.println("解密后:" + decryptDataOnJava(encode, PRI_KEY));
        System.out.println(Base64.encodeBase64String("luffy".getBytes("UTF-8")));
    }
}
