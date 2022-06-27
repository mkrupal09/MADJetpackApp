package com.example.mycomposecookbook.util.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class CustomShapes {

    @Composable
    @Preview
    fun AndroidLogo() {
        Canvas(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        ) {
            drawArc(Color.White, 180f, 180f, true)
            drawCircle(Color.Black, radius = 25.0f, center = Offset(size.center.x / 2, 130f))
            drawCircle(
                Color.Black,
                radius = 25.0f,
                center = Offset((size.center.x + (size.center.x / 2)), 130f),
            )

        }
    }
}