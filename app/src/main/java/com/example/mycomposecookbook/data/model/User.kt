package com.example.mycomposecookbook.data.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("avatar")
    val avatar: String
)

class UserPreviewParameter : PreviewParameterProvider<User> {
    override val values: Sequence<User>
        get() = sequenceOf(
            User("1", "krupal@grr.la", "krupal", "mehta", "https://picsum.photos/200/300"),
            User("1", "krupal@grr.la", "krupal", "mehta", "https://picsum.photos/200/300")

        )


}

