package com.example.mycomposecookbook.screen.home.location

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun LocationScreen(locationViewModel: LocationViewModel) {


    val locationState = locationViewModel.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        CameraPositionState(CameraPosition.fromLatLngZoom(LatLng(23.0225, 72.5714), 12.0f))
    }

    val locationMarker = rememberMarkerState("Unit", LatLng(0.0,0.0))
    var visible by remember { mutableStateOf(false) }

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



        val density = LocalDensity.current
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text("Hello,MN,MN,M", Modifier.fillMaxWidth().height(200.dp))
        }

    }

    LaunchedEffect(key1 = Unit, block = {
        locationViewModel.fetchCurrentLocation()
        visible=true
    })
}