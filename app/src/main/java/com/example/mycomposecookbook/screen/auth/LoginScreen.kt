package com.example.mycomposecookbook.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun LoginScreen(navController: NavController = NavController(LocalContext.current)) {
    rememberSystemUiController().setStatusBarColor(Color.White)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") } //


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
                errorMessage = emailError
            ) {
                email = it
            }

            MyEditText(
                hint = "Password",
                value = password,
                isPasswordField = true,
                margin = 10.dp,
                errorMessage = passwordError
            ) {
                password = it
            }

            MyButton(value = "Login", margin = 10.dp) {
                emailError = if (email.isEmpty()) "Please enter email" else ""
                passwordError = if (password.isEmpty()) "Please enter password" else ""

                if (emailError.isEmpty() && passwordError.isEmpty()) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
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

                navController.navigate("register?email=$email")

            }
        }
    }

}