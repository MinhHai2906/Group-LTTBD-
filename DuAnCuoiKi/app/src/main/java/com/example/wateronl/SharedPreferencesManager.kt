package com.example.wateronl

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("GioHangPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun luuDanhSachSanPham(danhSach: List<ThanhPhanUi>) {
        val json = gson.toJson(danhSach)
        sharedPreferences.edit {
            putString("DANH_SACH_SAN_PHAM", json)
        }
    }

    fun taiDanhSachSanPham(): List<ThanhPhanUi> {
        val json = sharedPreferences.getString("DANH_SACH_SAN_PHAM", null)
        if (json != null) {
            val type = object : TypeToken<List<ThanhPhanUi>>() {}.type
            return gson.fromJson(json, type)
        }
        return emptyList()
    }
}