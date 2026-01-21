package com.example.wateronl

import android.app.Application

class WaterOnlApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GioHangData.init(this)
    }
}