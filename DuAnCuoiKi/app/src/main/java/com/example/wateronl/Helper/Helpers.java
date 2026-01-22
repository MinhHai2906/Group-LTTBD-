package com.example.wateronl.Helper;

import android.annotation.SuppressLint;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Helpers {
    private static int transIdDefault = 1;

    // Khai báo trực tiếp AppID tại đây
    private static final String APP_ID = "554";

    @SuppressLint("DefaultLocale")
    public static String getAppTransId() {
        if (transIdDefault >= 100000) transIdDefault = 1;
        transIdDefault += 1;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        String dateString = format.format(new Date());
        return String.format("%s_%s_%s", dateString, APP_ID, System.currentTimeMillis());
    }

    public static String getMac(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        return HmacSHA256(data, key);
    }

    private static String HmacSHA256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        return toHexString(hmacSHA256.doFinal(data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b & 0xFF));
        return sb.toString();
    }
}