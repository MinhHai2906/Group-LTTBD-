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
    private val _hoTen = MutableStateFlow("Đang tải...")
    val hoTen = _hoTen.asStateFlow()
    private val _email = MutableStateFlow("...")
    val email = _email.asStateFlow()
    private val _sdt = MutableStateFlow("")
    val sdt = _sdt.asStateFlow()
    private val _diaChi = MutableStateFlow("")
    val diaChi = _diaChi.asStateFlow()
    private val _gioiTinh = MutableStateFlow("Nam")
    val gioiTinh = _gioiTinh.asStateFlow()
    private val _ngaySinh = MutableStateFlow("")
    val ngaySinh = _ngaySinh.asStateFlow()
    private val _nhanThongBao = MutableStateFlow(true)
    val nhanThongBao = _nhanThongBao.asStateFlow()
    private val _laTaiKhoanGoogle = MutableStateFlow(false)
    val laTaiKhoanGoogle = _laTaiKhoanGoogle.asStateFlow()
    private val _avatarCode = MutableStateFlow("avatar_1")
    val avatarCode = _avatarCode.asStateFlow()

    init {
        layThongTinCaNhan()
        kiemTraLoaiTaiKhoan()
    }

    fun kiemTraLoaiTaiKhoan() {
        val user = auth.currentUser
        val isGoogle = user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } ?: false
        _laTaiKhoanGoogle.value = isGoogle
    }

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

                        val gtDb = document.getString("gioiTinh") ?: "Nam"
                        val nsDb = document.getString("ngaySinh") ?: ""
                        val thongBaoDb = document.getBoolean("nhanThongBao") ?: true
                        if (ten != null) _hoTen.value = ten
                        if (sdtDb != null) _sdt.value = sdtDb
                        if (diaChiDb != null) _diaChi.value = diaChiDb
                        _avatarCode.value = avtDb
                        _gioiTinh.value = gtDb
                        _ngaySinh.value = nsDb
                        _nhanThongBao.value = thongBaoDb
                    }
                }
        }
    }

    fun dangXuat() {
        auth.signOut()
    }

    fun capNhatHoTen(tenMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("ten" to tenMoi)
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener { _hoTen.value = tenMoi }
        }
    }

    fun capNhatThongTinChiTiet(sdtMoi: String, diaChiMoi: String, gioiTinhMoi: String, ngaySinhMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf(
                "sdt" to sdtMoi,
                "diachi" to diaChiMoi,
                "gioiTinh" to gioiTinhMoi,
                "ngaySinh" to ngaySinhMoi
            )
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    _sdt.value = sdtMoi
                    _diaChi.value = diaChiMoi
                    _gioiTinh.value = gioiTinhMoi
                    _ngaySinh.value = ngaySinhMoi
                }
        }
    }
    fun capNhatCaiDat(nhanThongBaoMoi: Boolean) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("nhanThongBao" to nhanThongBaoMoi)
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    _nhanThongBao.value = nhanThongBaoMoi
                }
        }
    }

    fun doiAvatar(maAvatarMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("avatarCode" to maAvatarMoi)
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener { _avatarCode.value = maAvatarMoi }
        }
    }

    fun doiMatKhau(matKhauMoi: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        val user = auth.currentUser
        user?.updatePassword(matKhauMoi)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) onThanhCong() else onThatBai(task.exception?.message ?: "Lỗi đổi mật khẩu")
            }
    }
}