package com.example.mycomposecookbook.screen.insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.model.UserPreviewParameter
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class ProfileScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeCookBookTheme(darkTheme = false) {


                ProfileUi(
                    user = User(
                        "1",
                        "Krupal@gmail.com",
                        "Krupal",
                        "Mehta",
                        "\"https://randomuser.me/api/portraits/men/1.jpg"
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    fun ProfileUi(@PreviewParameter(UserPreviewParameter::class, limit = 1) user: User) {

        val systemUiCoontrolller = rememberSystemUiController()
        systemUiCoontrolller.setStatusBarColor(Color.Black, false)
        val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
        val coroutineScope = rememberCoroutineScope()
        var tabIndex by remember {
            mutableStateOf(0)
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    coroutineScope.launch {
                        bottomSheetState.collapse()
                    }
                })
            },
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .background(
                                color = Color.White,
                                RoundedCornerShape(5.dp)
                            )
                            .width(120.dp)
                            .height(10.dp)
                            .align(Alignment.TopCenter)
                    )

                    Row(modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "Share", modifier = Modifier.padding(20.dp))
                        Text(text = "Share", modifier = Modifier.padding(20.dp))
                        Text(text = "Share", modifier = Modifier.padding(20.dp))
                    }

                }
            },
            sheetBackgroundColor = Color.Gray,
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            sheetGesturesEnabled = true
        ) { _ ->
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row {
                    Column {
                        AsyncImage(
                            model = user.avatar,
                            placeholder = rememberAsyncImagePainter(model = R.drawable.jetpack),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(100.dp)
                                .border(3.dp, Color.Blue, CircleShape)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(10.dp)
                    ) {
                        Stats(title = "Posts", value = "15", modifier = Modifier.weight(1f))
                        Stats(title = "Followers", value = "15", modifier = Modifier.weight(1f))
                        Stats(
                            title = "Followings",
                            value = "15",
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
                Text(
                    text = user.firstName + user.lastName,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Row {
                    Text(text = "Story highlights", color = Color.White, fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Down",
                        tint = Color.White,
                    )
                }
                Text(
                    text = "Keep your favorite stories on your profile",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Stories(
                    list = arrayListOf("1", "2", "3"),
                    modifier = Modifier.padding(top = 10.dp)
                )

                TabRow(
                    selectedTabIndex = tabIndex,
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White, modifier = Modifier.padding(top = 10.dp)
                ) {
                    Tab(selected = tabIndex == 0, onClick = {
                        tabIndex = 0
                    }, modifier = Modifier.padding(5.dp)) {
                        Text(text = "Grid")
                    }
                    Tab(selected = tabIndex == 1, onClick = {
                        tabIndex = 1
                    }, modifier = Modifier.padding(5.dp)) {
                        Text(text = "List")
                    }
                    Tab(selected = tabIndex == 2, onClick = {
                        tabIndex = 2
                    }, modifier = Modifier.padding(5.dp)) {
                        Text(text = "Personal")
                    }
                }

                if (tabIndex == 0) {
                    PostsGrid(modifier = Modifier.padding(top = 10.dp))
                } else {
                    PostList(modifier = Modifier.padding(top = 10.dp)) {
                        coroutineScope.launch {
                            bottomSheetState.expand()
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun Stats(title: String, value: String, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = value, fontSize = 20.sp, color = Color.White)
            Text(text = title, color = Color.White, fontSize = 14.sp)
        }
    }

    @Composable
    fun Stories(list: ArrayList<String> = arrayListOf("1", "2", "3"), modifier: Modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
        ) {

            item {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .border(2.dp, Color.White, CircleShape)
                        .clipToBounds()
                ) {

                    Text(
                        text = "+",
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .padding(bottom = 2.dp)
                    )

                }
            }
            items(10) { item ->

                AsyncImage(
                    model = item, contentDescription = "image",
                    modifier = Modifier
                        .size(50.dp)
                        .border(2.dp, Color.Blue, CircleShape)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, Color.White)
                )
            }
        }
    }

    @Composable
    fun PostsGrid(modifier: Modifier) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        )
        {
            items(50) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color.White),
                    model = R.drawable.jetpack,
                    contentDescription = "image"
                )
            }
        }
    }

    @Composable
    fun PostList(modifier: Modifier, overflowClick: () -> Unit) {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            items(10) {
                Column {
                    Row {
                        AsyncImage(
                            model = R.drawable.jetpack,
                            contentDescription = "image",
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )

                        Text(
                            text = "Name here",
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(5.dp)
                                .weight(1f)
                        )

                        Icon(
                            painterResource(R.drawable.ic_overflow),
                            contentDescription = "menu",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                overflowClick()
                            }
                        )
                    }
                }

                AsyncImage(
                    model = R.drawable.jetpack,
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Row {
                    Icon(
                        painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "favorite",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )

                    Icon(
                        painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Message",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )

                    Icon(
                        painterResource(id = R.drawable.ic_send),
                        contentDescription = "send",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = "bookmark",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }

            }
        }
    }
}