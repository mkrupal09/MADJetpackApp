package com.example.mycomposecookbook.screen.insta

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mycomposecookbook.screen.scopedstorage.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {


    val postList = MutableStateFlow(
        arrayListOf(
            ImageModel(
                "",
                Uri.parse(""),
                "",
                false,
                false
            )
        )
    )

    suspend fun getPosts() = withContext(Dispatchers.IO){

        val list = arrayListOf<ImageModel>()
        for (i in 1..50) {
            list.add(
                ImageModel(
                    i.toString(),
                    Uri.parse(""),
                    "",
                    isSelected = false,
                    isFavorite = false
                )
            )
        }
        postList.value = list
    }
}