package com.chinacreator.c2.omp.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESCipher {
    private static String ENCODING = "UTF-8";
    
    public static byte[] encrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return cipher.doFinal(text);
    }

    public static byte[] decrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return cipher.doFinal(text);
    }

    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String temp = (Integer.toHexString(bytes[i] & 0XFF));
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) {
        try {
            String key  = args[0]; // 16bytes, 如"b3f60e6d1d5442a8"
            String text = args[1]; // 如"admin"

            System.out.printf("text in hex     : %s\n", bytes2hex(text.getBytes(ENCODING)));
            System.out.printf("text            : %s\n", text);

            byte[] enc = AESCipher.encrypt(text.getBytes(ENCODING), key.getBytes(ENCODING));
            System.out.printf("encrypt in hex  : %s\n", bytes2hex(enc));
            byte[] encInB64 = Base64.encodeBase64(enc);
            System.out.printf("encrypt in b64  : %s\n", new String(encInB64, ENCODING));

            byte[] decInB64 = Base64.decodeBase64(encInB64);
            System.out.printf("decrypt from b64: %s\n", bytes2hex(decInB64));
            byte[] dec = AESCipher.decrypt(decInB64, key.getBytes(ENCODING));
            System.out.printf("decrypt in hex  : %s\n", bytes2hex(dec));
            System.out.printf("decrypt         : %s\n", new String(dec, ENCODING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}