package com.example.wateronl

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    onDieuHuong: (String) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading_anim")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        ), label = "rotation"
    )
    val startAnimation = remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1500), label = "alpha"
    )

    // Logic điều hướng chính
    LaunchedEffect(key1 = true) {
        startAnimation.value = true
        delay(2500)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            onDieuHuong("trang_chu")
        } else {
            onDieuHuong("dang_nhap")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF7)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(alphaAnim.value)      // Áp dụng animation hiện dần vào toàn bộ cột
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MauCam.copy(alpha = 0.2f))
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Chờ một chút", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = MauNauDam)
            Text(
                "Có nước cho bạn ngay đây",
                fontSize = 16.sp,
                color = MauNauDam.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .size(40.dp)
                    .rotate(angle)
            ) {
                drawArc(
                    color = MauCam,
                    startAngle = 0f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}