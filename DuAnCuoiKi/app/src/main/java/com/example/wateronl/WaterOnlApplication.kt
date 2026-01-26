package com.example.wateronl

import android.app.Application
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

class WaterOnlApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GioHangData.init(this)
        ZaloPaySDK.init(554, Environment.SANDBOX)
    }
}