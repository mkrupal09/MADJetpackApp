package com.example.mycomposecookbook.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycomposecookbook.screen.home.location.LocationScreen
import com.example.mycomposecookbook.screen.home.setting.SettingScreen
import com.example.mycomposecookbook.screen.home.userDashboard.UserListHome

@ExperimentalFoundationApi
@Composable
@Preview
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   /* val usersState = viewModel.usersFlow.collectAsState(arrayListOf())*/

    val childNavController = rememberNavController()
    val navBackStackEntry by childNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        BottomNavigation {
            BottomNavigationItem(
                label = { Text(text = "Home") }, icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "test"
                    )
                }, onClick = {
                    childNavController.navigate("list")
                }, selected = currentRoute == "list"
            )
            BottomNavigationItem(
                label = { Text(text = "Location") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location"
                    )
                },
                onClick = {
                    childNavController.navigate("location") {

                    }
                },
                selected = currentRoute == "location"
            )
            BottomNavigationItem(
                label = { Text(text = "Settings") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                },
                onClick = {
                    childNavController.navigate("setting")
                },
                selected = currentRoute == "setting"
            )
        }
    }, drawerContent = {
        ModalDrawer(drawerContent = {}) {
            Text(text = "Test")
        }
    }, floatingActionButton = {
        if (currentRoute == "list") {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }) { innerPadding ->
        NavHost(
            navController = childNavController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") {
                UserListHome(viewModel)
            }
            composable("setting")
            {
                SettingScreen(navController)
            }
            composable("location")
            {
                LocationScreen()
            }
        }
    }


}