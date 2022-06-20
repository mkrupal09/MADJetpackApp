package com.example.mycomposecookbook.screen.scopedstorage

import android.graphics.Bitmap
import android.net.Uri
import java.io.File


data class ImageModel(
    val uri: Uri,
    val thumb: String,
    var isSelected: Boolean = false,
    var thumbBitmap: Bitmap? = null
)


/*
getMedia(Enviorment.getExternalStorageDirectory().listFiles())

fun getMedia(files: Array<File>?): List<ImageModel> {

    if (files == null)
        return arrayListOf()
    val media = mutableListOf<ImageModel>()
    for (file in files) {
        if (file.isDirectory) {
            media += getMedia(file.listFiles())
        } else {
            if (isAnImage(file.extension)) {
                media += ImageModel(file.path, file.name, file.length, file.lastModified())
            }
        }
    }
    return media
}*/
