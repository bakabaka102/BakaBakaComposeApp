package com.baka.composeapp.features.logicaction;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//https://viblo.asia/p/ma-hoa-du-lieu-voi-thuat-toan-aes-advanced-encryption-standard-trong-android-yMnKMLGz57P
public class AESHelper {
    private static final String ALGORITHM = "AES";
    private final static String HEX = "0123456789ABCDEF";

    // sử dụng hàm này khi cần mã hóa dữ liệu
    public static String encrypt(String seed, String clearText) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, clearText.getBytes());
        return toHex(result);
    }

    // sử dụng hàm này khi cần giải mã dữ liệu
    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);  // tạo ra các khóa bí mật có thể dùng lại
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); // tạo ra mã ngẫu nhiên
        sr.setSeed(seed); //Reseeds this random object, using the eight bytes contained in the given long seed.
        kgen.init(128, sr); // sử dụng AES-128
        SecretKey skey = kgen.generateKey();
        // return the raw key bytes as the result of a getEncoded method call.
        return skey.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    // chuyển đổi String thành mảng byte
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    // chuyển đổi một mảng byte trở thành String
    public static String toHex(byte[] buf) {
        if (buf == null) return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte b : buf) {
            appendHex(result, b);
        }
        return result.toString();
    }

    // dịch chuyển bit vào thêm mã hex
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
