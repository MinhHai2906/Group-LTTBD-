package com.example.appmuanuoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appmuanuoc.ui.theme.AppMuaNuocTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMuaNuocTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {
    var text2 by remember { mutableStateOf("") }
    var text1 by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize().padding(top=200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            text= text1,
            onValueChange = { text1 = it },
            label = "Nhap email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextInput(
            text= text2,
            onValueChange = { text2 = it},
            label = "Nhap so dien thoai"
        )
    }
}

@Composable
fun TextInput(text:String , onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor =Color.Blue,
            unfocusedBorderColor = Color.Blue,
        ),
        shape =RoundedCornerShape(18.dp)
    )
}
@Preview(showBackground = true, showSystemUi =true)
@Composable
fun GreetingPreview() {
    AppMuaNuocTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = Color.White

        ) {
            Greeting()
        }
    }
}
