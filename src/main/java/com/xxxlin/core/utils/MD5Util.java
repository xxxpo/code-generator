package com.xxxlin.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SuppressWarnings("unused")
public class MD5Util {

    /*盐值至少和哈希函数的输出一样长。(足够大的长度提供了足够多的盐值)*/
    private static final int SALT_LENGTH_BYTES = 18;        // 16byte = 128bit

    /*
     * 生成盐
     */
    public static byte[] createSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        try {
            // 强随机数生成, 用在较高场合
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 生成盐
     *
     * @param b 字节
     */
    public static byte[] createSalt(int b) {
        byte[] salt = new byte[b / 8];
        try {
            // 强随机数生成, 用在较高场合
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /*
     * @param salt 盐
     * @param psw 加密的数据
     * @return
     */
    public static String encrypt(byte[] salt, byte[] val) {
        byte[] dest = new byte[salt.length + val.length];
        System.arraycopy(salt, 0, dest, 0, salt.length);
        System.arraycopy(val, 0, dest, salt.length, val.length);
        return encrypt(dest);
    }

    public static byte[] add(byte[] salt, byte[] val) {
        byte[] dest = new byte[salt.length + val.length];
        System.arraycopy(salt, 0, dest, 0, salt.length);
        System.arraycopy(val, 0, dest, salt.length, val.length);
        return dest;
    }

    /*
     * 使用MD5加密
     */
    public static String encrypt(byte[] buffer) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        md5.reset();
        md5.update(buffer);
        final byte[] digest = md5.digest();
        return hex(digest);
    }

    public static String hex(final byte[] digest) {
        final BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    /**
     * 计算文件MD5
     * @param path 文件路径
     */
    public static String getMD5(String path) {
        try {
            byte[] buffer = new byte[1024*1024];
            int len;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            return hex(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
