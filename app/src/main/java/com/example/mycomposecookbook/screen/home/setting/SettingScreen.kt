package com.example.mycomposecookbook.screen.home.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
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

        val requestLogoutDialog = remember {
            mutableStateOf(false)
        }
        if (requestLogoutDialog.value) {
            AlertDialog(onDismissRequest = {
                requestLogoutDialog.value = false
            }, text = { Text(text = "Are you sure want to logout?", fontSize = 24.sp) }, buttons = {
                Row {
                    Text(
                        text = "Logout anyway",
                        color = Color.Blue,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("login") {

                                }
                            })

                    Text(text = "Cancel",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                requestLogoutDialog.value = false
                            })
                }
            })
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (profile, faq, privacy, logout, version, notification) = createRefs()

            val guidelineStart = createGuidelineFromStart(0.04f)
            val guidelinesEnd = createGuidelineFromEnd(0.04f)

            val chain = createVerticalChain(
                privacy,
                faq,
                privacy,
                logout,
                version,
                notification,
                chainStyle = ChainStyle.SpreadInside
            )

            val notificationState = remember {
                mutableStateOf(false)
            }

            Text(text = "Profile", modifier = Modifier.constrainAs(profile) {
                top.linkTo(parent.top, 30.dp)
                bottom.linkTo(faq.top)
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Faq", modifier = Modifier.constrainAs(faq) {
                top.linkTo(profile.bottom)
                bottom.linkTo(privacy.top)
                /* start.linkTo(profile.end)*/
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Privacy policy", modifier = Modifier.constrainAs(privacy) {
                top.linkTo(faq.bottom)
                bottom.linkTo(logout.top)
                /*start.linkTo(faq.end, 20.dp)*/
                start.linkTo(guidelineStart)
            }, fontSize = 30.sp)
            Text(text = "Logout", modifier = Modifier
                .constrainAs(logout) {
                    top.linkTo(privacy.bottom)
                    start.linkTo(guidelineStart)
                }
                .clickable {
                    requestLogoutDialog.value = true
                }, fontSize = 30.sp
            )
            Row(modifier = Modifier.constrainAs(notification) {
                top.linkTo(logout.bottom)
                start.linkTo(guidelineStart)
            }) {
                Text(text = "Notification", modifier = Modifier
                    .weight(1.0f)
                    .clickable {
                        notificationState.value = notificationState.value.not()
                    }, fontSize = 30.sp)
                Switch(
                    checked = notificationState.value,
                    modifier = Modifier.padding(10.dp),
                    onCheckedChange = {
                        notificationState.value = notificationState.value.not()
                    })
            }


            Text(text = "Version 1.0", modifier = Modifier.constrainAs(version) {
                bottom.linkTo(parent.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}