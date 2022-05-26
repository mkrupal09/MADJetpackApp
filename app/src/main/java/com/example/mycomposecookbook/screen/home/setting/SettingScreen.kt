package com.example.mycomposecookbook.screen.home.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
@Preview
fun SettingScreen(navController: NavController = NavController(LocalContext.current)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "Settings", modifier = Modifier.align(Alignment.CenterHorizontally))

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val profile = createRef()
            val faq = createRef()
            val privacy = createRef()
            val logout = createRef()
            val version = createRef()

            val guidelineStart = createGuidelineFromStart(0.04f)
            val guidelinesEnd = createGuidelineFromEnd(0.04f)

            Text(text = "Profile", modifier = Modifier.constrainAs(profile) {
                top.linkTo(parent.top, 30.dp)
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Faq", modifier = Modifier.constrainAs(faq) {
                top.linkTo(profile.bottom)
                /* start.linkTo(profile.end)*/
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Privacy policy", modifier = Modifier.constrainAs(privacy) {
                top.linkTo(faq.bottom)
                /*start.linkTo(faq.end, 20.dp)*/
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Logout", modifier = Modifier
                .constrainAs(logout) {
                    top.linkTo(privacy.bottom)
                    start.linkTo(guidelineStart)
                }
                .clickable {
                    navController.navigate("login") {

                    }
                }, fontSize = 30.sp
            )

            Text(text = "Version 1.0", modifier = Modifier.constrainAs(version) {
                bottom.linkTo(parent.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}