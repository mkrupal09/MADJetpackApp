package com.example.mycomposecookbook.data.model

data class ChatMessage(
    val id: String,
    val message: String,
    val senderId: String,
    val messageType: String = "text"
)