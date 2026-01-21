package com.example.wateronl.Helper;

import android.annotation.SuppressLint;

import com.example.wateronl.Constant.AppInfo;
import com.example.wateronl.Helper.HMac.HMacUtil;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Helpers {

    @NotNull
    @SuppressLint("DefaultLocale")
    public static String getAppTransId() {
        // Get the current date in yyMMdd format
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String dateString = format.format(new Date());

        // Format: yyMMdd_appid_od (where od is a unique string)
        // We use a timestamp to ensure uniqueness for this demo
        return String.format("%s_%s_%s", dateString, AppInfo.APP_ID, System.currentTimeMillis());
    }

    @NotNull
    public static String getMac(@NotNull String key, @NotNull String data) throws NoSuchAlgorithmException, InvalidKeyException {
        return Objects.requireNonNull(HMacUtil.HMacHexStringEncode(HMacUtil.HMACSHA256, key, data));
    }
}
