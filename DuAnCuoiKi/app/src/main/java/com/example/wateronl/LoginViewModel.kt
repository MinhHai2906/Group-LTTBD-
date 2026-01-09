package com.example.wateronl

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance() // them cai nay de sai firestore

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState = _loginState.asStateFlow()

    // sua ham nay: them tham so 'ten' de luu vao db
    fun dangKyTaiKhoan(email: String, matKhau: String, ten: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, matKhau)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // dang ky auth xong -> luu thong tin vao firestore
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = hashMapOf(
                            "uid" to uid,
                            "email" to email,
                            "ten" to ten,
                            "role" to "user" // mac dinh la khach hang
                        )

                        db.collection("users").document(uid).set(userMap)
                            .addOnSuccessListener {
                                _isLoading.value = false
                                _loginState.value = "OK"
                            }
                            .addOnFailureListener {
                                _isLoading.value = false
                                _loginState.value = "Lỗi lưu dữ liệu: ${it.message}"
                            }
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
                    // kiem tra xem user nay da co trong db chua, chua thi luu
                    val user = auth.currentUser
                    val uid = user?.uid
                    if (uid != null) {
                        val docRef = db.collection("users").document(uid)
                        docRef.get().addOnSuccessListener { document ->
                            if (!document.exists()) {
                                // user moi -> luu thong tin tu google vao
                                val userMap = hashMapOf(
                                    "uid" to uid,
                                    "email" to user.email,
                                    "ten" to user.displayName,
                                    "role" to "user"
                                )
                                docRef.set(userMap)
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

    fun quenMatKhau(email: String) {
        if (email.isNotEmpty()) auth.sendPasswordResetEmail(email)
    }

    fun resetState() { _loginState.value = null }
}