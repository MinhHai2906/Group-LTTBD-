package com.example.wateronl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrangChuContent() {
    var search by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp),
            colors = CardDefaults.cardColors(
                containerColor = MauCam.copy(alpha = 0.2f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Tìm kiếm sản phẩm", fontSize = 16.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 40.dp)
                        .shadow(elevation = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        Icon(
                            Icons.Default.Search, contentDescription = null,
                            modifier = Modifier.size(38.dp).padding(end = 10.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFD5AD90),
                        unfocusedBorderColor = MauCam.copy(alpha = 0.2f)
                    )
                )
                
                val buttonList = listOf(
                    "Tất cả",
                    "Nước ép",
                    "Caffe",
                    "Các loại trà",
                    "Yaourt",
                    "Soda",
                    "Trà sữa"
                )
                
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(buttonList) { nameDrink ->
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(20.dp),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.height(50.dp)
                        ) {
                            Text(text = nameDrink,
                                fontSize = 16.sp)
                        }
                    }
                }
            }// Box có outlinedtextfield
        }//Card
    }//Box
}//Trang chu conten

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTrangChu() {
    MaterialTheme {
        TrangChuContent()
    }
}
