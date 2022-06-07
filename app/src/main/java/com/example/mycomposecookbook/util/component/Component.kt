package com.example.mycomposecookbook.util.component

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.ui.theme.Purple200
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
@Preview
fun MyEditText(
    margin: Dp = 0.dp,
    hint: String = "Hint here",
    value: MutableState<String> = mutableStateOf(""),
    isPasswordField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String = "",
    onFocus: (Boolean) -> Unit = {},
    valueChange: (String) -> Unit = {}
) {
    //to manage state

    var passwordToggle by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin)
    ) {
        Box {
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(errorBorderColor = Color.Red),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = true,
                value = value.value,
                onValueChange = {
                    value.value = it
                    valueChange(it)
                },
                isError = errorMessage.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocus(it.hasFocus)
                    },
                label = { Text(text = hint) },
                visualTransformation = if (isPasswordField && passwordToggle.not()) PasswordVisualTransformation() else VisualTransformation.None
            )

            if (isPasswordField) {
                Image(
                    painter = painterResource(id = R.drawable.ic_eye),
                    contentDescription = "password toggle",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .clickable {
                            passwordToggle = passwordToggle.not()
                        }
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }

}

@Composable
@Preview
fun MyButton(
    margin: Dp = 0.dp,
    value: String = "Button",
    borderd: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .apply {
                if (borderd) {
                    border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Red)
                }
            }
            .fillMaxWidth()
            .padding(margin),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(text = value.uppercase(), modifier = Modifier.padding(8.dp))
    }
}

@Composable
@Preview
fun MyText(modifier: Modifier = Modifier, value: String = "Label", callback: () -> Unit = {}) {
    Text(text = value, modifier = modifier.clickable {
        callback()
    })
}

@Composable
@Preview
fun TopBarScreen(
    title: String = "Title",
    statusBarColor: androidx.compose.ui.graphics.Color = Purple200,
    backCallback: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    rememberSystemUiController().setStatusBarColor(Purple200)
    Scaffold(topBar = {
        TopAppBar(title = {
            MyText(value = title, modifier = Modifier.fillMaxWidth())
        }, navigationIcon = {
            IconButton(onClick = backCallback) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) {
        content()
    }
}

@Composable
@Preview
fun RowScope.NavigationItem(title: String = "", icon: ImageVector = Icons.Filled.Home) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxHeight()
            .weight(1.0f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = rememberVectorPainter(image = icon),
            contentDescription = title,
            colorFilter = ColorFilter.tint(
                Color.White
            )
        )
        Text(text = title)
    }
}

@Composable
fun MyAlertDialog(
    title: String,
    message: String,
    positiveButton: String = "",
    negativeButton: String,
    dismissRequest: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            dismissRequest(false)
        },
        buttons = {
            Column {
                MyButton(value = positiveButton, margin = 10.dp) {
                    dismissRequest(false)
                }
                MyButton(value = negativeButton, margin = 10.dp, borderd = true)
                {
                    dismissRequest(false)
                }
            }
        },
        title = { Text(text = title) },
        text = {
            Text(
                text = message
            )
        })
}