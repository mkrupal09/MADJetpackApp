package com.example.mycomposecookbook.data.model

import com.google.gson.annotations.SerializedName

class ListResponse<T> {
    @SerializedName("data")
    val list: ArrayList<T>? = null
}