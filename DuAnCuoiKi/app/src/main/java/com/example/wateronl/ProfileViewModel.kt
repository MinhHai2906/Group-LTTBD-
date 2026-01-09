package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.SetOptions

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Bien luu du lieu de hien thi len man hinh
    private val _hoTen = MutableStateFlow("Đang tải...")
    private val _laTaiKhoanGoogle = MutableStateFlow(false)

    val laTaiKhoanGoogle = _laTaiKhoanGoogle.asStateFlow()
    val hoTen = _hoTen.asStateFlow()

    private val _email = MutableStateFlow("...")
    val email = _email.asStateFlow()

    init {
        layThongTinCaNhan()
        kiemTraLoaiTaiKhoan()
    }
    fun kiemTraLoaiTaiKhoan() {
        val user = auth.currentUser
        // Kiem tra trong danh sach provider, neu co "google.com" thi la tai khoan Google
        val isGoogle = user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } ?: false
        _laTaiKhoanGoogle.value = isGoogle
    }
    fun layThongTinCaNhan() {
        val user = auth.currentUser
        val uid = user?.uid

        // Hien thi email lay tu Auth (chuan nhat)
        if (user?.email != null) {
            _email.value = user.email!!
        }

        // Lay ten tu Firestore
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val ten = document.getString("ten")
                        if (ten != null) _hoTen.value = ten
                    }
                }
        }
    }


    fun dangXuat() {
        auth.signOut()
    }
    // ham moi: cap nhat ten len firestore
    fun capNhatHoTen(tenMoi: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Tạo dữ liệu cần lưu
            val data = hashMapOf("ten" to tenMoi)

            // Dùng set + merge thay vì update
            // Ý nghĩa: Nếu chưa có hồ sơ thì tạo mới, có rồi thì chỉ sửa cái tên thôi
            db.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    // Cập nhật thành công -> Gán giá trị mới để giao diện tự đổi
                    _hoTen.value = tenMoi
                }
                .addOnFailureListener { e ->
                    // Nếu muốn soi lỗi thì bỏ comment dòng dưới
                    // e.printStackTrace()
                }
        }
    }
    // HAM MOI: Doi mat khau
    fun doiMatKhau(matKhauMoi: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        val user = auth.currentUser

        user?.updatePassword(matKhauMoi)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onThanhCong()
                } else {
                    // Neu loi (vi du: mat khau yeu, hoac lau qua chua dang nhap lai)
                    onThatBai(task.exception?.message ?: "Lỗi đổi mật khẩu")
                }
            }
    }
}


