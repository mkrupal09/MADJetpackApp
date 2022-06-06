package com.example.mycomposecookbook.screen.home.userDashboard


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.screen.home.HomeViewModel
import com.example.mycomposecookbook.screen.home.UserItem


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class) //For swipe feature
@Composable
fun UserListHome(viewModel: HomeViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUsers()
    }

    val lazyUsers: LazyPagingItems<User> = viewModel.usersFlow.collectAsLazyPagingItems()

    val loadingCallback = viewModel.userListLoading.collectAsState(initial = false)
    LazyColumn {
        items(lazyUsers) { user ->
            UserItem(user = user!!)
        }
        if (loadingCallback.value) {
            item {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}