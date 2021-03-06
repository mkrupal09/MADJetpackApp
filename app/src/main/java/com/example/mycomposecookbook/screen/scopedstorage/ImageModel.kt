package com.example.mycomposecookbook.screen.scopedstorage

import android.net.Uri


data class ImageModel(
    val name: String,
    val uri: Uri,
    val thumb: String,
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false
)