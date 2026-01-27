package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _mucDoHoanThien = MutableStateFlow(0f)
    val mucDoHoanThien = _mucDoHoanThien.asStateFlow()

    private val _hoTen = MutableStateFlow("")
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
    private val _hangThanhVien = MutableStateFlow("Thành viên Mới")
    val hangThanhVien = _hangThanhVien.asStateFlow()
    private val _tongTienTichLuy = MutableStateFlow(0L)
    val tongTienTichLuy = _tongTienTichLuy.asStateFlow()
    private val _daXacThucEmail = MutableStateFlow(true)
    val daXacThucEmail = _daXacThucEmail.asStateFlow()

    init {
        layThongTinCaNhan()
        kiemTraLoaiTaiKhoan()
        tinhHangThanhVien()
        kiemTraTrangThaiEmail()
        tinhMucDoHoanThien()
    }

    fun kiemTraTrangThaiEmail() {
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener {
            _daXacThucEmail.value = user.isEmailVerified                    //xác thực email
        }
    }

    fun guiLaiEmailXacThuc(onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        auth.currentUser?.sendEmailVerification()
            ?.addOnSuccessListener { onThanhCong() }
            ?.addOnFailureListener { e ->
                val loiTiengViet = if (e.message?.contains("blocked all requests") == true) {
                    "Bạn gửi yêu cầu quá nhanh. Vui lòng đợi vài phút rồi thử lại!"
                } else if (e is com.google.firebase.FirebaseNetworkException) {
                    "Lỗi kết nối mạng. Vui lòng kiểm tra lại."
                } else {
                    "Lỗi: ${e.message}"
                }
                onThatBai(loiTiengViet)
            }
    }

    private fun tinhMucDoHoanThien() {
        var diem = 0f
        if (_hoTen.value.isNotEmpty()) diem += 0.2f
        if (_sdt.value.isNotEmpty()) diem += 0.2f
        if (_diaChi.value.isNotEmpty()) diem += 0.2f
        if (_ngaySinh.value.isNotEmpty()) diem += 0.2f
        if (_avatarCode.value.isNotEmpty()) diem += 0.2f
        _mucDoHoanThien.value = diem
    }

    fun kiemTraLoaiTaiKhoan() {
        val user = auth.currentUser
        val isGoogle =
            user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } ?: false
        _laTaiKhoanGoogle.value = isGoogle
    }

    fun layThongTinCaNhan() {
        _isLoading.value = true
        val user = auth.currentUser
        val uid = user?.uid

        if (user?.email != null) _email.value = user.email!!

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
                    } else {
                        _avatarCode.value = "avatar_1"
                    }
                    tinhMucDoHoanThien()
                    _isLoading.value = false
                }
                .addOnFailureListener { _isLoading.value = false }
        } else {
            _isLoading.value = false
        }
    }

    fun tinhHangThanhVien() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("don_hang").whereEqualTo("uid", uid).get()        //truy vấn tài khoản để hiện đúng đơn hàng
            .addOnSuccessListener { documents ->
                var tongTien = 0L
                for (doc in documents) {
                    tongTien += doc.getDouble("tongTien")?.toLong() ?: 0L
                }
                _tongTienTichLuy.value = tongTien                                                   //tính hạng thành viên
                if (tongTien >= 5000000) _hangThanhVien.value = "Thành viên Vàng 👑"
                else if (tongTien >= 1000000) _hangThanhVien.value = "Thành viên Bạc 🥈"
                else _hangThanhVien.value = "Thành viên Mới"
            }
    }

    fun dangXuat() {
        auth.signOut()
    }

    fun capNhatHoTen(tenMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("ten" to tenMoi)
            db.collection("users").document(uid).set(data, SetOptions.merge())
                .addOnSuccessListener { _hoTen.value = tenMoi; tinhMucDoHoanThien() }
        }
    }

    fun capNhatThongTinChiTiet(
        sdtMoi: String,
        diaChiMoi: String,
        gioiTinhMoi: String,
        ngaySinhMoi: String
    ) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf(
                "sdt" to sdtMoi,
                "diachi" to diaChiMoi,
                "gioiTinh" to gioiTinhMoi,
                "ngaySinh" to ngaySinhMoi
            )
            db.collection("users").document(uid).set(data, SetOptions.merge())
                .addOnSuccessListener {
                    _sdt.value = sdtMoi; _diaChi.value = diaChiMoi; _gioiTinh.value =
                    gioiTinhMoi; _ngaySinh.value = ngaySinhMoi
                    tinhMucDoHoanThien()
                }
        }
    }

    fun doiAvatar(maAvatarMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("avatarCode" to maAvatarMoi)
            db.collection("users").document(uid).set(data, SetOptions.merge())
                .addOnSuccessListener { _avatarCode.value = maAvatarMoi; tinhMucDoHoanThien() }
        }
    }

    fun capNhatCaiDat(nhanThongBaoMoi: Boolean) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf("nhanThongBao" to nhanThongBaoMoi)
            db.collection("users").document(uid).set(data, SetOptions.merge())
                .addOnSuccessListener { _nhanThongBao.value = nhanThongBaoMoi }
        }
    }

    fun doiMatKhau(matKhauMoi: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        auth.currentUser?.updatePassword(matKhauMoi)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) onThanhCong() else onThatBai(
                    task.exception?.message ?: "Lỗi"
                )
            }
    }

    fun xoaTaiKhoan(matKhau: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        val user = auth.currentUser ?: return
        if (_laTaiKhoanGoogle.value) {                                                      //không thể đổi mk nếu đăng nhập gg
            db.collection("users").document(user.uid).delete()
            user.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) onThanhCong()
                else onThatBai("Vui lòng đăng xuất và đăng nhập lại để thực hiện!")
            }
            return
        }
        val credential = EmailAuthProvider.getCredential(user.email!!, matKhau)
        user.reauthenticate(credential)
            .addOnCompleteListener { reAuthTask ->
                if (reAuthTask.isSuccessful) {
                    db.collection("users").document(user.uid).delete()
                    user.delete().addOnCompleteListener { deleteTask ->
                        if (deleteTask.isSuccessful) onThanhCong()
                        else onThatBai(deleteTask.exception?.message ?: "Lỗi xóa")
                    }
                } else {
                    onThatBai("Mật khẩu không đúng!")
                }
            }
    }
}
