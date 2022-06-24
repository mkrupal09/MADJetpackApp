package com.example.mycomposecookbook.screen.insta

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mycomposecookbook.data.model.ChatMessage
import com.example.mycomposecookbook.screen.scopedstorage.MediaSelectionActivity
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import kotlinx.coroutines.launch

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
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()
        val messageList = viewModel.list
        fun scrollToBottom() {
            coroutineScope.launch {
                scrollState.animateScrollToItem(messageList.size - 1, 0)
            }
        }

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.sendMedia(it.data?.getStringExtra("media") ?: "")
                scrollToBottom()
            }

        val permissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    launcher.launch(
                        Intent(
                            this@MessageScreen,
                            MediaSelectionActivity::class.java
                        )
                    )
                }
            }

        Column {
            Messages(modifier.weight(1f), messageList, scrollState)
            InputArea(onMessageSend = {
                viewModel.sendMessage(it)
                scrollToBottom()
            }, onAttachment = {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            })
        }

    }

    @Composable
    @Preview
    private fun InputArea(
        modifier: Modifier = Modifier,
        onMessageSend: (String) -> Unit = {},
        onAttachment: () -> Unit = {}
    ) {

        var text by remember {
            mutableStateOf("")
        }
        Box(modifier = modifier) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                trailingIcon = {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "send",
                        modifier = Modifier.clickable {
                            onMessageSend(text)
                            text = ""
                        })
                }, leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "attachment",
                        modifier = Modifier.clickable {
                            onAttachment()
                        })
                }
            )

        }

    }

    @Composable
    private fun Messages(
        modifier: Modifier,
        messageList: SnapshotStateList<ChatMessage>,
        scrollState: LazyListState
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier,
            state = scrollState
        ) {
            items(messageList) {
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
                            model = it.message,
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