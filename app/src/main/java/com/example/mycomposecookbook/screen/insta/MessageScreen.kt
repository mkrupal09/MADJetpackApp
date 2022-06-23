package com.example.mycomposecookbook.screen.insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mycomposecookbook.data.model.ChatMessage
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme

class MessageScreen : ComponentActivity() {


    val myUserId = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val viewModel: MessageViewModel = viewModel()
            MyComposeCookBookTheme {
                ChatUi(viewModel, modifier = Modifier.padding(10.dp))
            }

            LaunchedEffect(key1 = Unit, block = {
                viewModel.fetchMessage()
            })
        }


    }


    data class ChatConfig(val alignment: Alignment, val color: Color)

    @Composable
    fun ChatUi(
        viewModel: MessageViewModel,
        modifier: Modifier
    ) {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
            items(viewModel.list) {
                Box(modifier = Modifier.fillMaxWidth()) {

                    val (alignment, background) = if (it.senderId == myUserId) {
                        ChatConfig(Alignment.TopEnd, Color.Blue)
                    } else {
                        ChatConfig(Alignment.TopStart, Color.Black)
                    }

                    if (it.messageType == "text") {
                        Text(
                            text = it.message,
                            modifier = Modifier
                                .align(alignment)
                                .background(background, RoundedCornerShape(10.dp))
                                .padding(15.dp),
                            color = Color.White
                        )
                    } else if (it.messageType == "image") {
                        AsyncImage(
                            model = "https://picsum.photos/200/300",
                            contentDescription = "media",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .align(alignment),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}