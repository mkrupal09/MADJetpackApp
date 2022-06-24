package com.example.mycomposecookbook.screen.insta

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposecookbook.data.model.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {

    val myUserId = "1"

    val list = mutableStateListOf<ChatMessage>()


    fun fetchMessage() {
        viewModelScope.launch(Dispatchers.IO) {

            list.add(ChatMessage("1", "Hello", "0", dateTime = "23 Oct 2022"))
            list.add(ChatMessage("1", "Hi krupal here", myUserId, dateTime = "23 Oct 2022"))



            delay(500)
            list.add(ChatMessage("1", "How are you doing?", myUserId, dateTime = "23 Oct 2022"))

            delay(500)
            list.add(
                ChatMessage(
                    "0",
                    "I'm fine. how you?",
                    "0",
                    messageType = "text",
                    dateTime = "23 Nov 2022"
                )
            )
            list.add(
                ChatMessage(
                    "0",
                    "https://picsum.photos/200/300",
                    "0",
                    messageType = "image",
                    "23 Nov 2022"
                )
            )
            list.add(
                ChatMessage(
                    "0",
                    "https://picsum.photos/200/300",
                    "1",
                    messageType = "image",
                    "25 Dec 2022"
                )
            )
            list.add(
                ChatMessage(
                    "0",
                    "https://picsum.photos/200/300",
                    "1",
                    messageType = "image",
                    "25 Dec 2022"
                )
            )
            list.add(
                ChatMessage(
                    "0",
                    "https://picsum.photos/200/300",
                    "1",
                    messageType = "image",
                    "25 Dec 2022"
                )
            )
        }
    }


    fun sendMessage(text: String) {
        list.add(ChatMessage("0", text, myUserId, messageType = "text", dateTime = "31 Dec 2022"))
    }

    fun sendMedia(url: String) {
        list.add(ChatMessage("0", url, myUserId, messageType = "image", dateTime = "31 Dec 2022"))
    }

    /*fun sendAttachment(uri:String){
        list.add(ChatMessage("0",))
    }*/

}