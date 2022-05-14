package com.example.mycomposecookbook.screen.auth

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.mycomposecookbook.util.component.TopBarScreen

@Composable
@Preview
fun WebViewScreen(
    navController: NavController = NavController(LocalContext.current),
    title: String = "",
    url: String = ""
) {
    TopBarScreen(title = title, backCallback = {
        navController.navigateUp()
    }) {
        AndroidView(factory = { it ->
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })
    }
}
