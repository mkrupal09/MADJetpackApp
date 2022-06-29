package com.example.mycomposecookbook.screen.home.userDashboard


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.screen.home.HomeViewModel
import com.example.mycomposecookbook.screen.home.UserItem
import com.example.mycomposecookbook.screen.insta.ProfileScreen
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class) //For swipe feature
@Composable
fun UserListHome(viewModel: HomeViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUsers()
    }

    val lazyUsers: LazyPagingItems<User> = viewModel.usersFlow.collectAsLazyPagingItems()
    val loadingCallback = viewModel.userListLoading.collectAsState(initial = false)
    val (value, onValueChange) = remember { mutableStateOf("") }
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()

    val x= derivedStateOf {

    }

    val context=LocalContext.current

    fun toggleBottomSheet() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                bottomSheetScaffoldState.bottomSheetState.expand()
            else
                bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetGesturesEnabled = true,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Black)
            ) {
                Text(
                    text = "Hello from sheet",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                TextField(
                    value = value,
                    onValueChange = {

                        onValueChange(it)
                    },
                    textStyle = TextStyle(fontSize = 17.sp),
                    leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(Color(0xFFE7F1F1), RoundedCornerShape(16.dp)),
                    placeholder = { Text(text = "Search ") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.DarkGray
                    )
                )

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(lazyUsers) { user ->
                        UserItem(user = user!!, modifier = Modifier.clickable {
                            openProfileScreen(context)
                        })
                    }
                    item {
                        if (loadingCallback.value) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(25.dp),
                onClick = {
                    toggleBottomSheet()
                }) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Add",
                    modifier = Modifier.rotate(if (bottomSheetScaffoldState.bottomSheetState.isExpanded) 45.0f else 0.0f)
                )
            }
        }
    }


}


fun openProfileScreen(context: Context) {
    context.startActivity(
        Intent(
            context,
            ProfileScreen::class.java
        )
    )
}