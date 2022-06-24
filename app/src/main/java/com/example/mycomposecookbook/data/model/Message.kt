package com.example.mycomposecookbook.data.model

data class ChatMessage(
    val id: String,
    val message: String,
    val senderId: String,
    val messageType: String = "text",
    val dateTime: String = ""
) {
    companion object {
        const val MESSAGE_TYPE_TEXT = "text"
        const val MESSAGE_TYPE_IMAGE = "image"
        const val MESSAGE_TYPE_VIDEO = "video"
        const val MESSAGE_TYPE_DOCUMENT = "document"
    }
}

