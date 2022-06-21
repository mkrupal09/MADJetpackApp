package com.example.mycomposecookbook.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme

class SideEffectsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeCookBookTheme {
                val cope = rememberCoroutineScope()

                val updatedState = rememberUpdatedState(newValue = {

                })
                LaunchedEffect(key1 = "Ad") {

                }

                SideEffect {

                }
            }
        }
    }
}