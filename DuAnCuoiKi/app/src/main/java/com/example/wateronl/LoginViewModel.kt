package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()       // Công cụ xác thực của Firebase (Email/Google)
    private val db = FirebaseFirestore.getInstance()    // Công cụ lưu trữ database người dùng

    // MutableStateFlow: Dòng dữ liệu trạng thái, dùng để báo cho UI biết app đang Load hay đã xong
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()             // UI chỉ được đọc (StateFlow), không được sửa trực tiếp

    // Lưu trạng thái kết quả đăng nhập (null: chưa làm gì, "OK": thành công, "Lỗi...": thất bại)
    private val _loginState = MutableStateFlow<String?>(null)
    val loginState = _loginState.asStateFlow()

    // Hàm chuyển sang TViet
    private fun chuyenLoiSangTiengViet(e: Exception?): String {
        return when (e) {
            is com.google.firebase.auth.FirebaseAuthInvalidUserException -> "Tài khoản không tồn tại hoặc bị khóa."
            is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> "Sai email hoặc mật khẩu."
            is com.google.firebase.auth.FirebaseAuthUserCollisionException -> "Email này đã được đăng ký."
            is com.google.firebase.FirebaseNetworkException -> "Lỗi kết nối mạng. Vui lòng kiểm tra lại."
            else -> {
                val msg = e?.message ?: ""
                if (msg.contains("supplied auth credential")) "Phiên đăng nhập hết hạn hoặc lỗi xác thực."
                else "Lỗi: $msg"
            }
        }
    }

    // Xử lý đăng ký: Tạo tài khoản trên Auth -> Nếu xong thì tạo thêm Document thông tin ở Firestore
    fun dangKyTaiKhoan(email: String, matKhau: String, ten: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, matKhau)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid
                    if (uid != null) {
                        user.sendEmailVerification()
                        val userMap = hashMapOf(        // Tạo Map dữ liệu để lưu vào Firestore
                            "uid" to uid,
                            "email" to email,
                            "ten" to ten,
                            "role" to "user"            // Mặc định tài khoản mới là khách hàng
                        )
                        db.collection("users").document(uid).set(userMap)
                            .addOnSuccessListener {
                                _isLoading.value = false; _loginState.value = "OK"
                            }
                            .addOnFailureListener {
                                _isLoading.value = false
                                _loginState.value = "Lỗi lưu: ${it.message}"
                            }
                    }
                } else {
                    _isLoading.value = false
                    _loginState.value = chuyenLoiSangTiengViet(task.exception)
                }
            }
    }

    // Xử lý đăng nhập bằng Email và Password
    fun dangNhap(email: String, matKhau: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, matKhau)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginState.value = "OK"
                } else {
                    _loginState.value = chuyenLoiSangTiengViet(task.exception)
                }
            }
    }

    // Xử lý đăng nhập bằng Google (Sử dụng Token từ Google Sign-In)
    fun dangNhapGoogle(idToken: String) {
        _isLoading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser      // Kiểm tra nếu người dùng Google lần đầu vào app thì mới lưu thông tin vào Firestore
                    val uid = user?.uid
                    if (uid != null) {
                        db.collection("users").document(uid).get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    val userMap = hashMapOf(
                                        "uid" to uid,
                                        "email" to user.email,
                                        "ten" to user.displayName,
                                        "role" to "user"
                                    )
                                    db.collection("users").document(uid).set(userMap)
                                }
                                _isLoading.value = false
                                _loginState.value = "OK"
                            }
                    }
                } else {
                    _isLoading.value = false
                    _loginState.value = chuyenLoiSangTiengViet(task.exception)
                }
            }
    }

    // Gửi email khôi phục mật khẩu
    fun quenMatKhau(email: String, onThanhCong: () -> Unit, onThatBai: (String) -> Unit) {
        if (email.isEmpty()) {
            onThatBai("Vui lòng nhập Email!")
            return
        }
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onThanhCong()
                } else {
                    onThatBai(chuyenLoiSangTiengViet(task.exception))
                }
            }
    }

    // Xóa trạng thái cũ để tránh việc UI bị nhảy màn hình lặp lại
    fun resetState() {
        _loginState.value = null
    }
}