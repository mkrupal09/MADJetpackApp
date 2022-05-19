package com.example.mycomposecookbook.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.util.component.MyButton
import com.example.mycomposecookbook.util.component.MyEditText
import com.example.mycomposecookbook.util.component.MyText
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
@Preview
fun LoginScreen(
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController = NavController(LocalContext.current)
) {
    rememberSystemUiController().setStatusBarColor(Color.White)

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = viewModel.emailError.collectAsState()
    val passwordError = viewModel.passwordError.collectAsState()
    val navigateHome = viewModel.navigateHome.collectAsState()


    LaunchedEffect(navigateHome.value) {
        if (navigateHome.value) {
            navController.navigate("home")
        }
    }


    /*val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val (showSnackBar, setShowSnackBar) = remember {
        mutableStateOf(false)
    }*/


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.jetpack),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center),
                )
            }

            MyEditText(
                hint = "Email",
                value = email,
                margin = 10.dp,
                keyboardType = KeyboardType.Email,
                errorMessage = emailError.value
            ) {
                email.value = it
            }

            MyEditText(
                hint = "Password",
                value = password,
                isPasswordField = true,
                margin = 10.dp,
                errorMessage = passwordError.value
            ) {
                password.value = it
            }

            MyButton(value = "Login", margin = 10.dp) {
                viewModel.login(email.value, password.value)

            }

            MyText(
                value = "Forgot Password?", modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 10.dp)
            ) {
                navController.navigate("forgot")
            }

            Spacer(modifier = Modifier.weight(1.0f))

            MyButton(value = "Create an account", margin = 10.dp) {
                navController.navigate("register?email=${email.value}")
            }
        }
    }

}