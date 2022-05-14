package com.example.mycomposecookbook.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycomposecookbook.util.component.MyButton
import com.example.mycomposecookbook.util.component.MyEditText
import com.example.mycomposecookbook.util.component.TopBarScreen

@Composable
@Preview
fun RegistrationScreen(
    navController: NavController = NavController(LocalContext.current),
    email: String = ""
) {
    val scrollState = rememberScrollState()
    val agreeCondition = remember {
        mutableStateOf(false)
    }

    TopBarScreen(title = "New Registration", statusBarColor = Color.White, backCallback = {
        navController.navigateUp()
    }) {
        Surface(modifier = Modifier.fillMaxHeight()) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.scrollable(scrollState, Orientation.Horizontal)
            ) {
                MyEditText(hint = "First Name", margin = 10.dp) {

                }
                MyEditText(hint = "Last Name", margin = 10.dp) {

                }
                MyEditText(
                    hint = "Email",
                    margin = 10.dp,
                    keyboardType = KeyboardType.Email,
                    value = email
                ) {

                }
                MyEditText(hint = "Mobile", margin = 10.dp, keyboardType = KeyboardType.Phone) {

                }
                MyEditText(hint = "Password", margin = 10.dp, isPasswordField = true) {

                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = agreeCondition.value, onCheckedChange = {
                        agreeCondition.value = it
                    })
                    Text(text = "Agree")
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = "Terms & conditions", modifier = Modifier.clickable {
                        navController.navigate("web?title=Terms condition&url=https://www.google.com")
                    }, textDecoration = TextDecoration.Underline)
                }
                MyButton(value = "Register", margin = 10.dp) {

                }
            }
        }
    }
}