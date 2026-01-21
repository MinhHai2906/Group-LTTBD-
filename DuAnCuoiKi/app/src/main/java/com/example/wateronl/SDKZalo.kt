package com.example.wateronl

import android.app.Application
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

class SDKZalo : Application() {
    override fun onCreate() {
        super.onCreate()
        ZaloPaySDK.init(554, Environment.SANDBOX)
    }
}
