package com.daqsoft.commonnanning.common;

/**
 * AES加解密算法
 * 此处使用AES-128-CBC加密模式，key需为16位。
 * Author znb
 * on 2018/2/12.
 */

import android.util.Base64;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptUtil {

    /**
     * 密匙
     */
    private static final String KEY = "AQ4S10D7d9K8c64D";
    private static final String KEY16 = "js7ksl3nhnfivl4m";

    /**
     * iv向量
     */
    private static final String IV = "dwvNVXzyXiq37u-A";
    private static final String IV16 = "3859345501849051";

    /**
     * 加密
     * @param word
     * @return
     */
    public static String Encrypt(String word) throws Exception {
        byte[] raw = KEY.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，加入iv向量，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//        byte[] encrypted = cipher.doFinal(word.getBytes());
        word = new String(word.getBytes(), "UTF-8");
        return Base64.encodeToString(cipher.doFinal(word.getBytes()),Base64.DEFAULT);
//        return Base64.encodeBase64String(encrypted);//此处使用BAES64做转码功能，同时能起到2次加密的作用。
    }
    /**
     * 加密
     * 严博
     * @param word
     * @return
     */
    public static String Encrypt16(String word) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(IV16.getBytes());
        SecretKeySpec key = new SecretKeySpec(KEY16.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key,zeroIv);
        byte[] encryptedData = cipher.doFinal(word.getBytes("UTF-8"));
        byte[] encode = new Hex().encode(encryptedData);
        return new String(encode);
    }
}
