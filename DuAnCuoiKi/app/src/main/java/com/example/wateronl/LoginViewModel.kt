package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState = _loginState.asStateFlow()

    fun dangKyTaiKhoan(email: String, matKhau: String, ten: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, matKhau)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = hashMapOf("uid" to uid, "email" to email, "ten" to ten, "role" to "user")
                        db.collection("users").document(uid).set(userMap)
                            .addOnSuccessListener { _isLoading.value = false; _loginState.value = "OK" }
                            .addOnFailureListener { _isLoading.value = false; _loginState.value = "Lỗi lưu: ${it.message}" }
                    }
                } else {
                    _isLoading.value = false
                    _loginState.value = task.exception?.message ?: "Đăng ký thất bại"
                }
            }
    }

    fun dangNhap(email: String, matKhau: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, matKhau)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) _loginState.value = "OK"
                else _loginState.value = task.exception?.message ?: "Lỗi đăng nhập"
            }
    }

    fun dangNhapGoogle(idToken: String) {
        _isLoading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid
                    if (uid != null) {
                        db.collection("users").document(uid).get().addOnSuccessListener { document ->
                            if (!document.exists()) {
                                val userMap = hashMapOf("uid" to uid, "email" to user.email, "ten" to user.displayName, "role" to "user")
                                db.collection("users").document(uid).set(userMap)
                            }
                            _isLoading.value = false
                            _loginState.value = "OK"
                        }
                    }
                } else {
                    _isLoading.value = false
                    _loginState.value = task.exception?.message ?: "Lỗi Google"
                }
            }
    }
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
                    onThatBai(task.exception?.message ?: "Lỗi gửi mail")
                }
            }
    }
    fun resetState() { _loginState.value = null }
}