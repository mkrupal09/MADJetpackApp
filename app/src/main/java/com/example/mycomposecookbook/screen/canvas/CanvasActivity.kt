package com.example.mycomposecookbook.screen.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import kotlinx.coroutines.cancel

class CanvasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyComposeCookBookTheme {

                /*  CanvasContent()*/

                Column {
                    Text(text = "Hello world",
                        modifier = Modifier
                            .drawBehind {
                                drawCircle(
                                    color = Color.Blue,
                                    radius = 50.0f,
                                    center = Offset(size.width / 2, size.height / 2),
                                )
                            }
                        ,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    fun CanvasContent() {



        Canvas(modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
            .height(300.dp), onDraw = {
            drawRect(color = Color.Black, size = size)
            drawRect(
                color = Color.Red,
                topLeft = Offset(100.0f, 100.0f),
                size = androidx.compose.ui.geometry.Size(100f, 100f),
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Blue
                    ),
                    center = Offset(50.0f, 50.0f)
                ),
                radius = 100f,
                center = center
            )

            drawLine(
                color = Color.Red,
                start = Offset(160.0f, 300.0f),
                end = Offset(160.0f, 400.0f),
                strokeWidth = 10.0f
            )

        })
    }
}