package com.ssm.common.base.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public abstract class EncryptUtils {

    public static final String DEFAULT_KEY = "CHINESE SOFTWARE";
    public static final String ENCODING = "UTF-8";
    public static final String ALGORITHM = "AES";
    public static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator.getInstance(ALGORITHM).init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ALGORITHM));
        return cipher.doFinal(content.getBytes(ENCODING));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator.getInstance(ALGORITHM).init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ALGORITHM));
        return new String(cipher.doFinal(encryptBytes));
    }

    // public static byte[] md5Hash(String source) throws NoSuchAlgorithmException {
    //     MessageDigest md5 = MessageDigest.getInstance("MD5");
    //     md5.update(source.getBytes());
    //     return md5.digest();
    // }

    // public static void main(String[] args) throws Exception {
    //     String encryptStr = EncryptUtils.aesEncrypt("root", DEFAULT_KEY);
    //     String decryptStr = EncryptUtils.aesDecrypt(encryptStr, DEFAULT_KEY);
    //     System.out.println(encryptStr);
    //     System.out.println(decryptStr);
    // }

}
