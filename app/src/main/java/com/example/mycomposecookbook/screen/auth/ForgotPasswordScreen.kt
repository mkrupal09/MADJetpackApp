package com.example.mycomposecookbook.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycomposecookbook.util.component.*

@Composable
@Preview
fun ForgotPasswordScreen(navController: NavController = NavController(LocalContext.current)) {

    val showAlert = remember { mutableStateOf(false) }

    TopBarScreen(title = "Forgot Password", statusBarColor = Color.White, backCallback = {
        navController.navigateUp()
    }) {
        Surface(modifier = Modifier.fillMaxHeight()) {

            if (showAlert.value) {
                MyAlertDialog(
                    title = "Confirmation",
                    message = "Are you sure want to send email?",
                    positiveButton = "Yes, Proceed",
                    negativeButton = "Cancel",
                    dismissRequest = {
                        showAlert.value = false
                    })
            }

            Column(verticalArrangement = Arrangement.Center) {
                MyEditText(
                    hint = "Email Address",
                    margin = 10.dp,
                    keyboardType = KeyboardType.Email
                ) {

                }

                MyButton(value = "Request Password", margin = 10.dp) {

                    showAlert.value = true
                }
            }
        }
    }
}
