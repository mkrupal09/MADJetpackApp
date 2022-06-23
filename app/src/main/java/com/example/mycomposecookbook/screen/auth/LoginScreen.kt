package com.example.mycomposecookbook.screen.auth

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
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

    val email = remember { mutableStateOf("eve.holt@reqres.in") }
    val password = remember { mutableStateOf("cityslicka") }
    val emailError = viewModel.emailError.collectAsState()
    val passwordError = viewModel.passwordError.collectAsState()
    val navigateHome = viewModel.navigateHome.collectAsState(false)
    var logoVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current


    val logoScale = remember {
        androidx.compose.animation.core.Animatable(0.5f)
    }

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
            AnimatedVisibility(
                visible = logoVisible,
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 3000)) {

                    with(density) { -100.dp.roundToPx() }
                }) {
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
                            .scale(logoScale.value)
                            .align(Alignment.Center),
                    )
                }
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

            LazyHorizontalGrid(
                contentPadding = PaddingValues(0.dp),
                rows = GridCells.Fixed(3),
                modifier = Modifier
                    .height(200.dp)
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(50, key = {
                        it
                    }) { item ->
                        AsyncImage(
                            model = "https://randomuser.me/api/portraits/men/$item.jpg",
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .border(1.dp, Color.Red, CircleShape)
                                .padding(2.dp)
                                .clip(CircleShape)
                        )
                    }
                })

            Spacer(modifier = Modifier.weight(1.0f))
            MyButton(value = "Create an account", margin = 10.dp) {
                navController.navigate("register?email=${email.value}")
            }


            //to animate logo
            LaunchedEffect(key1 = Unit, block = {
                logoVisible = true


                logoScale.animateTo(
                    targetValue = 1.0f,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = { OvershootInterpolator(2f).getInterpolation(it) })
                )
            })
        }
    }

}