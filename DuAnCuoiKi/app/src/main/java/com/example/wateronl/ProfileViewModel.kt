package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // --- CAC BIEN HIEN THI ---
    private val _hoTen = MutableStateFlow("Đang tải...")
    val hoTen = _hoTen.asStateFlow()

    private val _email = MutableStateFlow("...")
    val email = _email.asStateFlow()

    private val _sdt = MutableStateFlow("")
    val sdt = _sdt.asStateFlow()

    private val _diaChi = MutableStateFlow("")
    val diaChi = _diaChi.asStateFlow()

    private val _laTaiKhoanGoogle = MutableStateFlow(false)
    val laTaiKhoanGoogle = _laTaiKhoanGoogle.asStateFlow()

    private val _avatarCode = MutableStateFlow("avatar_1") // Mặc định là avatar_1
    val avatarCode = _avatarCode.asStateFlow()

    init {
        layThongTinCaNhan()
        kiemTraLoaiTaiKhoan()
    }

    // Kiem tra nguon goc tai khoan
    fun kiemTraLoaiTaiKhoan() {
        val user = auth.currentUser
        val isGoogle = user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } ?: false
        _laTaiKhoanGoogle.value = isGoogle
    }

    // Lay thong tin day du (Ten, Email, SDT, DiaChi, Avatar)
    fun layThongTinCaNhan() {
        val user = auth.currentUser
        val uid = user?.uid

        if (user?.email != null) {
            _email.value = user.email!!
        }

        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val ten = document.getString("ten")
                        val sdtDb = document.getString("sdt")
                        val diaChiDb = document.getString("diachi")
                        val avtDb = document.getString("avatarCode") ?: "avatar_1"

                        if (ten != null) _hoTen.value = ten
                        if (sdtDb != null) _sdt.value = sdtDb
                        if (diaChiDb != null) _diaChi.value = diaChiDb
                        _avatarCode.value = avtDb
                    }
                }
        }
    }

    fun dangXuat() {
        auth.signOut()
    }

    // Cap nhat ten
    fun capNhatHoTen(tenMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("ten" to tenMoi)
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener { _hoTen.value = tenMoi }
        }
    }

    // Cap nhat SDT va Dia Chi
    fun capNhatThongTinChiTiet(sdtMoi: String, diaChiMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf(
                "sdt" to sdtMoi,
                "diachi" to diaChiMoi
            )
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    _sdt.value = sdtMoi
                    _diaChi.value = diaChiMoi
                }
        }
    }

    // Cap nhat Avatar
    fun doiAvatar(maAvatarMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("avatarCode" to maAvatarMoi)
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    _avatarCode.value = maAvatarMoi
                }
        }
    }

    // Doi mat khau
    fun doiMatKhau(matKhauMoi: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        val user = auth.currentUser
        user?.updatePassword(matKhauMoi)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onThanhCong()
                } else {
                    onThatBai(task.exception?.message ?: "Lỗi đổi mật khẩu")
                }
            }
    }
}