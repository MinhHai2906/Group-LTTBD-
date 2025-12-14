package com.example.appmuanuoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appmuanuoc.ui.theme.AppMuaNuocTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight as FontWeigh
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.sp

class FirstScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMuaNuocTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier, onNavigateToLogin: () -> Unit) {
    Box(
        modifier=Modifier.fillMaxSize().background(Color(0xFFFDF6E3)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Xin Chào",
                modifier = Modifier.padding(top = 80.dp),
                fontWeight = FontWeigh.Bold,
                fontSize = 45.sp
            )

            Text(
                text = "quý khách",
                modifier = Modifier.padding(top = 2.dp),
                fontWeight = FontWeigh.Bold,
                fontSize = 40.sp,
                color = Color(0xFFD4A373)
            )
            Text(
                text = "Quán nước online của Quang và Hải",
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.padding(top = 80.dp)
                    .size(250.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .border(BorderStroke(2.dp, Color.White), CircleShape)
                    .clip(CircleShape)
            )
        }//Colum text

            Text(text="Bấm vào đây để tiếp tục",
                modifier=Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .clickable { onNavigateToLogin() },
                color = Color.Gray,
                fontSize = 20.sp)
    }
}


@Preview(showBackground = true,showSystemUi= true)
@Composable
fun FirstScreenPreview() {
    AppMuaNuocTheme {
        Greeting(onNavigateToLogin = {})
    }
}
