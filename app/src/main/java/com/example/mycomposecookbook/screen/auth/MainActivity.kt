package com.example.mycomposecookbook.screen.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycomposecookbook.screen.base.BaseComponentActivity
import com.example.mycomposecookbook.screen.home.HomeScreen
import com.example.mycomposecookbook.screen.home.HomeViewModel
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyComposeCookBookTheme(darkTheme = false) {
                val navController = rememberNavController()
                /*NavHost to AnimatedNavHost for animation*/
                NavHost(navController = navController, startDestination = "login") {
                    //Here register all screen
                    composable("login") { LoginScreen(navController) }
                    composable("forgot") { ForgotPasswordScreen(navController) }
                    composable("register?email={email}", arguments = listOf(navArgument("email") {
                        type = NavType.StringType
                        defaultValue = ""
                    })) { backStackEntry ->
                        RegistrationScreen(
                            navController,
                            backStackEntry.arguments?.getString("email") ?: ""
                        )
                    }
                    composable(
                        "web?title={title}&url={url}",
                        arguments = listOf(navArgument("url") {
                            type = NavType.StringType
                            defaultValue = ""
                        }, navArgument("title") {
                            type = NavType.StringType
                            defaultValue = ""
                        })
                    ) { navBackStackEntry ->
                        WebViewScreen(
                            navController = navController,
                            title = navBackStackEntry.arguments?.getString("title") ?: "",
                            url = navBackStackEntry.arguments?.getString("url") ?: ""
                        )
                    }
                    composable("home") {
                        val vm = hiltViewModel<HomeViewModel>()
                        HomeScreen(navController, vm)
                    }
                }
            }
        }


    }
}