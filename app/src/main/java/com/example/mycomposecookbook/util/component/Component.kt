package com.example.mycomposecookbook.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycomposecookbook.ui.theme.Purple200
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
@Preview
fun MyEditText(
    margin: Dp = 0.dp,
    hint: String = "Hint here",
    value: String = "",
    isPasswordField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    valueChange: (String) -> Unit = {}
) {
    var valueFill by remember {
        mutableStateOf(value)
    } //to manage state

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        value = valueFill,
        onValueChange = {
            valueFill = it
            valueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin),
        label = { Text(text = hint) },
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
@Preview
fun MyButton(margin: Dp = 0.dp, value: String = "Button", onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
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
            .wrapContentSize(Alignment.Center).fillMaxHeight()
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