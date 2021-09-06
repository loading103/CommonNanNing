package com.daqsoft.utils;

/**
 * AES加解密算法
 * 此处使用AES-128-CBC加密模式，key需为16位。
 * @author MouJunFeng
 * @time 2018-4-25
 * @since JDK 1.8
 * @version 1.0.0
 */

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptUtil {

    /**
     * 密匙
     */
    private static final String KEY = "AQ4S10D7d9K8c64D";

    /**
     * iv向量
     */
    private static final String IV = "dwvNVXzyXiq37u-A";

    /**
     * 加密
     *
     * @param word
     * @return
     * @throws Exception
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
     * 解密
     *
     * @param dWord
     * @return
     * @throws Exception
     */
  /*  public static String Decrypt(String dWord) throws Exception {
        try {
            byte[] raw = KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(dWord);//先用bAES64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
