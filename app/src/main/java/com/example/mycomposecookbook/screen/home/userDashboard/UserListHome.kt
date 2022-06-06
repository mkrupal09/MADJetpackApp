package com.example.mycomposecookbook.screen.home.userDashboard


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.screen.home.UserItem as UserItem1


@OptIn(ExperimentalMaterialApi::class) //For swipe feature
@Composable
fun UserListHome(users: ArrayList<User> = arrayListOf()) {
    LazyColumn(content = {
        items(users) { item ->
            val dismissState = rememberDismissState(confirmStateChange = {
                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                    users.remove(item)
                }
                true
            })
            SwipeToDismiss(
                state = dismissState,
                background = { Box {} },
                dismissThresholds = { FractionalThreshold(2.0f) }) {
                UserItem1(user = item)
            }
        }
    }, verticalArrangement = Arrangement.spacedBy(5.dp), contentPadding = PaddingValues(10.dp))
}