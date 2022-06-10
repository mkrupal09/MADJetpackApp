package com.example.mycomposecookbook.screen.home.location

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay


val userComposible = compositionLocalOf<String> { "test" }

@Composable
fun LocationScreen(locationViewModel: LocationViewModel) {

    CompositionLocalProvider(userComposible provides "testx1") {

    }

    val locationState = locationViewModel.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        CameraPositionState(CameraPosition.fromLatLngZoom(LatLng(23.0225, 72.5714), 12.0f))
    }

    val requestShowDialog = remember {
        mutableStateOf(false)
    }

    val locationMarker = rememberMarkerState("Unit", LatLng(0.0, 0.0))

    val settingResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {

        }
    )

    var showToast by remember {
        mutableStateOf(false)
    }

    if (showToast) {
        Toast.makeText(LocalContext.current, userComposible.current, Toast.LENGTH_LONG).show()
    }

    if (requestShowDialog.value) {
        MaterialTheme
        AlertDialog(
            onDismissRequest = {
                requestShowDialog.value = false
            },
            buttons = {
                Text(text = "Open Image Picker", modifier = Modifier.clickable {
                    settingResultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    requestShowDialog.value = false
                })
            },
            title = { Text(text = "Choose Image ${userComposible.current}") },
            text = { Text(text = "Please pick image from") })
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize()
        ) {
            Marker(
                state = locationMarker,
                title = "Your Title",
                snippet = "Place Name"
            )
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        locationViewModel.fetchCurrentLocation()

        delay(2000)

        showToast = true
        /* delay(2000)

         requestShowDialog.value = true*/
    })

}