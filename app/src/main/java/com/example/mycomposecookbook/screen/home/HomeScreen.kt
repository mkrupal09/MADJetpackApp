package com.example.mycomposecookbook.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycomposecookbook.data.model.User

@Composable
@Preview
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val usersState = viewModel.usersFlow.collectAsState(arrayListOf())
    Scaffold(bottomBar = {
        BottomNavigation {
            BottomNavigationItem(
                label = { Text(text = "Home") }, icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "test"
                    )
                }, onClick = {}, selected = false
            )
            BottomNavigationItem(
                label = { Text(text = "Location") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location"
                    )
                },
                onClick = {},
                selected = false
            )
            BottomNavigationItem(
                label = { Text(text = "Settings") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                },
                onClick = {},
                selected = false
            )
        }
    }, drawerContent = {
        ModalDrawer(drawerContent = {}) {
            Text(text = "Test")
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }) {
        UserList(users = usersState.value)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUsers()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserList(users: ArrayList<User>) {

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
                UserItem(user = item)
            }
        }
    }, verticalArrangement = Arrangement.spacedBy(5.dp), contentPadding = PaddingValues(10.dp))
}

