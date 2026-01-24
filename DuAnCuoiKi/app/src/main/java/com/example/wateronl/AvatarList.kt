package com.example.wateronl

object AvatarList {          // tạo ra một Singleton
    val danhSach = mapOf(    // ánh xạ giữa một cái tên và ID hình ảnh
        "avatar_1" to R.drawable.avatar_1,
        "avatar_2" to R.drawable.avatar_2,
        "avatar_3" to R.drawable.avatar_3,
        "avatar_4" to R.drawable.avatar_4,
        "avatar_5" to R.drawable.avatar_5,
        "avatar_6" to R.drawable.avatar_6,
        "avatar_7" to R.drawable.avatar_7,
        "avatar_8" to R.drawable.avatar_8,
        "avatar_9" to R.drawable.avatar_9,
        "avatar_10" to R.drawable.avatar_10
    )

    fun layAnhTuMa(code: String?): Int {    // nhận vào một mã String trả ID ảnh tương ứng
        return danhSach[code] ?: R.drawable.avatar_1
    }
}