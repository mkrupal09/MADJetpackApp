package com.example.mycomposecookbook.util.extension

import android.content.Context
import coil.request.ImageRequest

fun Context.imageLoader(url: String, placeHolder: Int): ImageRequest {
    return ImageRequest.Builder(this).data(url).placeholder(placeHolder).crossfade(true).build()
}