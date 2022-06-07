package com.example.mycomposecookbook.data.model

import com.google.gson.annotations.SerializedName

class ListResponse<T> {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_pages")
    var totalPage: Int = 0

    @SerializedName("data")
    val list: ArrayList<T>? = null
}