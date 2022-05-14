package com.example.mycomposecookbook.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.util.component.NavigationItem

@Composable
@Preview
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val usersState = viewModel.usersFlow.collectAsState(arrayListOf())
    Scaffold(bottomBar = {
        BottomNavigation {
            NavigationItem(title = "Home", icon = Icons.Filled.Home)
            NavigationItem(title = "Location", icon = Icons.Filled.LocationOn)
            NavigationItem(title = "Settings", icon = Icons.Filled.Settings)
        }
    }, drawerContent = {
        ModalDrawer(drawerContent = {}) {
            Text(text = "Test")
        }
    }) {
        UserList(users = usersState.value)
    }

    viewModel.fetchUsers()
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn(content = {
        items(users) { item ->
            UserItem(user = item)
        }
    }, verticalArrangement = Arrangement.spacedBy(5.dp), contentPadding = PaddingValues(10.dp))
}

