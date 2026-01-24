package com.example.wateronl

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ManHinhCho(
    onDieuHuong: (String) -> Unit       // Hàm nhận vào route để chuyển màn hình
) {
    // Animation xoay tạo hiệu ứng quay cho vòng tròn loading ở dưới
    val infiniteTransition = rememberInfiniteTransition(label = "loading_anim")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing) // 1 vòng trong 1 giây
        ), label = "rotation"
    )

    // Animation hiện dần   logo và chữ sẽ mờ sau đó hiện rõ dần
    val startAnimation = remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,     // Từ 0 (ẩn) đến 1 (hiện rõ)
        animationSpec = tween(durationMillis = 1500), label = "alpha"
    )

    // Logic điều hướng chính
    LaunchedEffect(key1 = true) {       // Kích hoạt animation hiện dần ngay khi vào màn hình
        startAnimation.value = true
        delay(2500) // Đợi 2.5 giây để người dùng kịp nhìn thấy thương hiệu/logo

        val auth = FirebaseAuth.getInstance()
        // Kiểm tra xem người dùng đã đăng nhập từ trước chưa
        if (auth.currentUser != null) {
            onDieuHuong("trang_chu")        // Nếu rồi, đưa thẳng vào trang chủ
        } else {
            onDieuHuong("dang_nhap")        // Nếu chưa, đưa về màn hình đăng nhập
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF7)),
        contentAlignment = Alignment.Center
    ) {
        // logo và text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(alphaAnim.value)      // Áp dụng animation hiện dần vào toàn bộ cột
        ) {
            // Hiển thị Logo
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
                Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(MauCam.copy(alpha = 0.2f)))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Chờ một chút", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
            Text("Có nước cho bạn ngay đây", fontSize = 16.sp, color = MauNauDam.copy(alpha = 0.6f), modifier = Modifier.padding(top = 8.dp))
        }

        Box(        // Vẽ vòng tròn Loading bằng Canvas
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .size(40.dp)
                    .rotate(angle)      // Áp dụng animation xoay đã khai báo
            ) {
                // Vẽ một vòng cung hở
                drawArc(
                    color = MauCam,
                    startAngle = 0f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)      // StrokeCap.Round làm cho đầu cung tròn bị bo tròn
                )
            }
        }
    }
}