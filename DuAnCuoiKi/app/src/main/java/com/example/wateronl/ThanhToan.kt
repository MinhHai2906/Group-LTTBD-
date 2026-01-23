package com.example.wateronl

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wateronl.Api.CreateOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ThanhToan(
    onBackClick: () -> Unit,
    navController: NavController,
    activity: Activity,
    viewModel: ProfileViewModel = viewModel()
) {
    val itemsToPay = ThanhToanData.danhSachThanhToan
    val totalPrice = itemsToPay.sumOf { it.price * it.increasing }
    val userName by viewModel.hoTen.collectAsState()
    var note by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("Nhấn để chọn địa chỉ") }
    val paymentOptions = listOf("Thanh toán khi nhận hàng", "Thanh toán chuyển khoản")
    var selectedPaymentOption by remember { mutableStateOf(paymentOptions[0]) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val paymentHandled = remember { java.util.concurrent.atomic.AtomicBoolean(false) }

    // Lắng nghe kết quả chọn địa chỉ từ MapScreen
    val newAddressResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("selected_address")
        ?.observeAsState()

    newAddressResult?.value?.let {
        address = it
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<String>("selected_address")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(50.dp)
                    .padding(start = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back",
                    tint = MauCam,
                    modifier = Modifier.size(50.dp)
                )
            }
            Text(
                text = "Thanh toán",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            items(itemsToPay) { item ->
                ThanhToanItem(item = item)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    text = "Tên người nhận: $userName",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            // Card chọn địa chỉ
            item {
                DiaChiCard(
                    address = address,
                    onEditAddress = {
                        navController.navigate("map_screen")
                    }
                )
            }

            // Input số điện thoại
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        // SỬA: Chuyển sang bàn phím số
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                    )
                    if (phoneNumber.isEmpty()) {
                        Text(
                            text = buildAnnotatedString {
                                append("Số điện thoại")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(" (Bắt buộc)")
                                }
                            },
                            color = Color.Gray
                        )
                    }
                }
            }

            // Input ghi chú
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = note,
                        onValueChange = { note = it },
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                    )
                    if (note.isEmpty()) {
                        Text(text = "Ghi chú", color = Color.Gray)
                    }
                }
            }

            // Chọn phương thức thanh toán
            item {
                Column {
                    paymentOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { selectedPaymentOption = text })
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedPaymentOption),
                                onClick = { selectedPaymentOption = text },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MauCam
                                )
                            )
                            Text(
                                text = text,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Card Tổng tiền & Nút thanh toán
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(130.dp),
            shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tổng tiền",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "${totalPrice}đ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // === NÚT XÁC NHẬN THANH TOÁN ===
                Button(
                    onClick = {
                        // SỬA: Logic Validate số điện thoại chặt chẽ hơn
                        val isPhoneValid = phoneNumber.length == 10 &&
                                phoneNumber.startsWith("0") &&
                                phoneNumber.all { it.isDigit() }

                        // 1. Kiểm tra thông tin đầu vào
                        if (address == "Nhấn để chọn địa chỉ") {
                            Toast.makeText(
                                context,
                                "Vui lòng chọn địa chỉ nhận hàng",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (phoneNumber.isBlank()) {
                            Toast.makeText(
                                context,
                                "Vui lòng nhập số điện thoại",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!isPhoneValid) {
                            Toast.makeText(
                                context,
                                "Số điện thoại phải bắt đầu bằng 0 và đủ 10 số",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // 2. Chuẩn bị dữ liệu Đơn hàng
                            val auth = FirebaseAuth.getInstance()
                            val user = auth.currentUser
                            val maDonHang = "DH_${System.currentTimeMillis()}"
                            val thoiGianHienTai = System.currentTimeMillis()
                            val sdf = java.text.SimpleDateFormat(
                                "dd/MM/yyyy HH:mm",
                                java.util.Locale.getDefault()
                            )
                            val ngayDep = sdf.format(java.util.Date(thoiGianHienTai))
                            val danhSachMonAn = itemsToPay.map {
                                ChiTietDonHang(it.namedrink, it.image, it.increasing, it.price)
                            }
                            val donHangMoi = DonHang(
                                id = maDonHang,
                                uid = user?.uid ?: "",
                                tenNguoiNhan = userName,
                                sdt = phoneNumber,
                                diaChi = address,
                                ghiChu = note,
                                tongTien = totalPrice.toLong(),
                                phuongThuc = selectedPaymentOption,
                                daThanhToan = false,
                                trangThai = 0,
                                ngayDat = ngayDep,
                                timestamp = thoiGianHienTai,
                                danhSachMon = danhSachMonAn
                            )

                            // Hàm phụ trợ để lưu đơn hàng lên Firebase
                            fun luuLenFirebase(donHang: DonHang) {
                                val db = FirebaseFirestore.getInstance()
                                db.collection("don_hang").document(donHang.id).set(donHang)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Đặt hàng thành công!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // SỬA: Chuyển về trang_chu, báo cho nó biết cần xóa giỏ hàng và mở tab 2
                                        navController.navigate("trang_chu?tabIndex=2&clearCart=true") {
                                            popUpTo("trang_chu") { inclusive = true }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            context,
                                            "Lỗi lưu đơn: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }

                            // 3. Xử lý Logic Thanh Toán
                            if (selectedPaymentOption == "Thanh toán chuyển khoản") {
                                // LOGIC ZALOPAY
                                coroutineScope.launch {
                                    try {
                                        val orderApi = CreateOrder()
                                        val itemsJson = JSONArray()
                                        itemsToPay.forEach {
                                            val item = JSONObject()
                                            item.put("itemid", it.id)
                                            item.put("itemname", it.namedrink)
                                            item.put("itemprice", it.price)
                                            item.put("itemquantity", it.increasing)
                                            itemsJson.put(item)
                                        }

                                        // Gọi API tạo đơn hàng
                                        val order = withContext(Dispatchers.IO) {
                                            orderApi.createOrder(
                                                totalPrice.toLong().toString(),
                                                itemsJson.toString()
                                            )
                                        }

                                        if (order != null && order.has("returncode")) {
                                            val code = order.getInt("returncode")
                                            if (code == 1) {
                                                val token = order.getString("zptranstoken")
                                                ZaloPaySDK.getInstance().payOrder(
                                                    activity,
                                                    token,
                                                    "demozpdk://app",
                                                    object : PayOrderListener {
                                                        override fun onPaymentSucceeded(
                                                            transactionId: String?,
                                                            transToken: String?,
                                                            appTransID: String?
                                                        ) {
                                                            if (paymentHandled.compareAndSet(false, true)) {
                                                                activity.runOnUiThread {
                                                                    donHangMoi.daThanhToan = true
                                                                    donHangMoi.ghiChu += " (Đã thanh toán qua ZaloPay)"
                                                                    luuLenFirebase(donHangMoi)
                                                                }
                                                            }
                                                        }

                                                        override fun onPaymentCanceled(
                                                            zpTransToken: String?,
                                                            appTransID: String?
                                                        ) {
                                                            if (!paymentHandled.get()) {
                                                                activity.runOnUiThread {
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Đã hủy thanh toán ZaloPay",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }

                                                        override fun onPaymentError(
                                                            zaloPayError: ZaloPayError?,
                                                            zpTransToken: String?,
                                                            appTransID: String?
                                                        ) {
                                                           if (!paymentHandled.get()) {
                                                                activity.runOnUiThread {
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Lỗi thanh toán ZaloPay",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }
                                                    }
                                                )
                                            } else {
                                                val msg = order.getString("returnmessage")
                                                Toast.makeText(
                                                    context,
                                                    "Lỗi ZaloPay: $msg",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Lỗi tạo đơn: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        e.printStackTrace()
                                    }
                                }
                            } else {
                                // --- LOGIC TIỀN MẶT ---
                                luuLenFirebase(donHangMoi)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 8.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MauCam),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Text(
                        text = "Xác nhận thanh toán",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// UI Con: Card địa chỉ
@Composable
fun DiaChiCard(address: String, onEditAddress: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditAddress() }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                tint = MauCam,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Địa chỉ nhận hàng", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = " (Bắt buộc)",
                        fontSize = 10.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = address,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 3
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

// UI Con: Item món ăn trong danh sách thanh toán
@Composable
fun ThanhToanItem(item: ThanhPhanUi) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8)),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.namedrink,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.namedrink,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(text = "Số lượng: ${item.increasing}", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = "${item.price}đ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MauCam
                )
            }
        }
    }
}
