package com.example.mycomposecookbook.util.component

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
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
@Preview
fun getWeatherApp() {
    val backgroundColor = listOf(Color(0xFF2078EE), Color(0xFF74E6FE))
    val sunColor = listOf(Color(0xFFFFC200), Color(0xFFFFE100))
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(width.times(.76f), height.times(.72f))
            cubicTo(
                width.times(.93f),
                height.times(.72f),
                width.times(.98f),
                height.times(.41f),
                width.times(.76f),
                height.times(.40f)
            )
            cubicTo(
                width.times(.75f),
                height.times(.21f),
                width.times(.35f),
                height.times(.21f),
                width.times(.38f),
                height.times(.50f)
            )
            cubicTo(
                width.times(.25f),
                height.times(.50f),
                width.times(.20f),
                height.times(.69f),
                width.times(.41f),
                height.times(.72f)
            )
            close()
        }
        drawRoundRect(
            brush = Brush.verticalGradient(backgroundColor),
            cornerRadius = CornerRadius(50f, 50f),

            )
        drawCircle(
            brush = Brush.verticalGradient(sunColor),
            radius = width.times(.17f),
            center = Offset(width.times(.35f), height.times(.35f))
        )
        drawPath(path = path, color = Color.White.copy(alpha = .90f))
    }
}

