package com.example.mycomposecookbook.screen.insta

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.data.model.ChatMessage
import com.example.mycomposecookbook.screen.scopedstorage.MediaSelectionActivity
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import com.example.mycomposecookbook.util.component.TopBarScreen
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MessageScreen : AppCompatActivity() {

    val myUserId = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MessageViewModel = viewModel()
            MyComposeCookBookTheme {
                ChatUi(viewModel, modifier = Modifier.padding(10.dp))
            }
        }

        Giphy.configure(this, "IZxP6pTpqeo9lvewLRh7zEwmS1GO3g9g")
    }

    data class ChatConfig(val alignment: Alignment, val color: Color)

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ChatUi(
        viewModel: MessageViewModel,
        modifier: Modifier
    ) {
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()
        val messageList = viewModel.list

        var openGallery by remember {
            mutableStateOf(false)
        }
        var selectedMessage: ChatMessage? by remember {
            mutableStateOf(null)
        }

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

        val safLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val uri = it?.data?.data
                if (uri != null) {
                    viewModel.sendMedia(uri.toString())
                }
            }

        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
            AttachmentPickerBottomSheet(onGallery = {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }, onAttachment = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "*/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                safLauncher.launch(intent)
            })
        }) {
            TopBarScreen(title = "Message", statusBarColor = MaterialTheme.colors.primary) {
                Column {
                    Messages(modifier.weight(1f), messageList, scrollState, onMediaTap = {
                        openGallery = true
                        selectedMessage = it
                    })
                    InputArea(onMessageSend = {
                        viewModel.sendMessage(it)
                        scrollToBottom()
                    }, onAttachment = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }, onEmoji = {
                        val giphy = GiphyDialogFragment.newInstance()
                        giphy.gifSelectionListener =
                            object : GiphyDialogFragment.GifSelectionListener {
                                override fun didSearchTerm(term: String) {

                                }

                                override fun onDismissed(selectedContentType: GPHContentType) {

                                }

                                override fun onGifSelected(
                                    media: Media,
                                    searchTerm: String?,
                                    selectedContentType: GPHContentType
                                ) {
                                    viewModel.sendMedia(media.images.original?.gifUrl ?: "")
                                    scrollToBottom()
                                }
                            }
                        giphy.show(supportFragmentManager, "s")
                    })
                }
            }
        }

        if (openGallery) {
            GalleryPager(
                list = messageList.filter { selectedMessage!!.messageType == "image" },
                currentIndex = messageList.indexOf(selectedMessage)
            )
        }

        BackHandler {
            if (openGallery) {
                selectedMessage = null
                openGallery = false
            } else {
                finish()
            }
        }

        LaunchedEffect(key1 = Unit, block = {
            viewModel.fetchMessage()
        })
    }


    @Composable
    fun AttachmentPickerBottomSheet(onGallery: () -> Unit = {}, onAttachment: () -> Unit) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {

            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "gallery", colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.Gray, CircleShape)
                        .padding(20.dp)
                        .clickable {
                            onGallery()
                        }
                )
                Text(text = "Gallery")
            }

            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_document),
                    contentDescription = "document", colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.Gray, CircleShape)
                        .padding(20.dp)
                        .clickable {
                            onAttachment()
                        }
                )
                Text(text = "Document")
            }
        }
    }

    @Composable
    @Preview
    private fun InputArea(
        modifier: Modifier = Modifier,
        onMessageSend: (String) -> Unit = {},
        onAttachment: () -> Unit = {},
        onEmoji: () -> Unit = {}
    ) {
        var text by remember {
            mutableStateOf("")
        }
        Box(modifier = modifier) {
            TextField(
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onSend = {
                    onMessageSend(text)
                }),
                value = text,
                maxLines = 5,
                placeholder = {
                    Text(text = "Type message here..")
                },
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = "attachment",
                            modifier = Modifier.clickable {
                                onAttachment()
                            })
                        Icon(
                            painterResource(id = R.drawable.ic_emoji),
                            contentDescription = "emoji",
                            modifier = Modifier.clickable {
                                onEmoji()
                            })
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Messages(
        modifier: Modifier,
        messageList: SnapshotStateList<ChatMessage>,
        scrollState: LazyListState,
        onMediaTap: (ChatMessage) -> Unit = {}
    ) {
        val groupedMessage = messageList.groupBy { it.dateTime }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier,
            state = scrollState
        ) {

            groupedMessage.forEach { (date, messages) ->
                stickyHeader {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = date,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .background(
                                    Color.Gray,
                                    RoundedCornerShape(15.dp)
                                )
                                .padding(horizontal = 15.dp, vertical = 5.dp),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                }
                items(messages) {
                    Message(it = it, onMediaTap = onMediaTap)
                }
            }

        }
    }


    @Composable
    private fun Message(it: ChatMessage, onMediaTap: (ChatMessage) -> Unit = {}) {
        Box(modifier = Modifier.fillMaxWidth()) {

            val (alignment, background) = if (it.senderId == myUserId) {
                ChatConfig(Alignment.TopEnd, MaterialTheme.colors.primary)
            } else {
                ChatConfig(Alignment.TopStart, MaterialTheme.colors.secondary)
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
                SubcomposeAsyncImage(
                    model = it.message,
                    contentDescription = "media",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(alignment)
                        .clickable {
                            onMediaTap(it)
                        },
                    loading = {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(
                                        Alignment.Center
                                    )
                            )
                        }
                    },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun GalleryPager(list: List<ChatMessage>, currentIndex: Int) {

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        Box {
            HorizontalPager(
                list.size,
                state = pagerState,
                modifier = Modifier.background(Color.Black)
            ) {
                SubcomposeAsyncImage(
                    model = list[currentPage].message,
                    contentDescription = "image",
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                )
            }
            Text(
                text = "${pagerState.currentPage} / ${pagerState.pageCount}",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 30.dp)
            )
        }

        LaunchedEffect(key1 = Unit, block = {
            coroutineScope.launch {
                pagerState.scrollToPage(currentIndex)
            }
        })
    }
}