package com.example.wateronl

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.Locale

@Composable
fun MapScreen(
    initialAddress: String?,
    onAddressSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val hcmcPoint = remember { GeoPoint(10.7769, 106.7009) }
    var selectedPoint by remember { mutableStateOf(hcmcPoint) }
    val scope = rememberCoroutineScope()
    var isGeocoding by remember { mutableStateOf(false) }

    LaunchedEffect(initialAddress) {
        if (!initialAddress.isNullOrBlank()) {
            scope.launch(Dispatchers.IO) {
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocationName(initialAddress, 1)
                    if (addresses?.isNotEmpty() == true) {
                        val location = addresses[0]
                        val newPoint = GeoPoint(location.latitude, location.longitude)
                        withContext(Dispatchers.Main) {
                            selectedPoint = newPoint
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    controller.setZoom(15.0)
                    controller.setCenter(hcmcPoint)

                    val marker = Marker(this)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    overlays.add(marker)
                    tag = marker // Store marker in tag

                    val eventsReceiver = object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                            selectedPoint = p
                            return true
                        }

                        override fun longPressHelper(p: GeoPoint): Boolean {
                            return false
                        }
                    }
                    val eventsOverlay = MapEventsOverlay(eventsReceiver)
                    overlays.add(0, eventsOverlay)

                    invalidate()
                }
            },
            update = { mapView ->
                mapView.controller.animateTo(selectedPoint)
                (mapView.tag as? Marker)?.position = selectedPoint
                mapView.invalidate()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
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
                text = "Chọn địa chỉ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MauCam,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                isGeocoding = true
                val point = selectedPoint
                val geocoder = Geocoder(context, Locale.getDefault())

                scope.launch {
                    try {
                        val addressText = withTimeout(10000L) { // 10-second timeout
                            withContext(Dispatchers.IO) {
                                @Suppress("DEPRECATION")
                                val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
                                addresses?.firstOrNull()?.getAddressLine(0) ?: "Không tìm thấy địa chỉ"
                            }
                        }
                        onAddressSelected(addressText)
                    } catch (e: TimeoutCancellationException) {
                        onAddressSelected("Lỗi: Thời gian chờ lấy địa chỉ quá lâu.")
                    } catch (e: Exception) {
                        onAddressSelected("Lỗi khi lấy địa chỉ")
                    } finally {
                        isGeocoding = false
                    }
                }
            },
            enabled = !isGeocoding,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 45.dp, vertical = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE59C54))
        ) {
            if (isGeocoding) {
                Text("Đang xử lý...")
            } else {
                Text("Xác nhận địa chỉ này")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMap() {
    MaterialTheme {
        MapScreen(onAddressSelected = {}, onBackClick = {}, initialAddress = null)
    }
}
