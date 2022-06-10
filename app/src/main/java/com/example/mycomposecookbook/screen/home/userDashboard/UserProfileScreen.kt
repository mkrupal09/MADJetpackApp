package com.example.mycomposecookbook.screen.home.userDashboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.model.UserPreviewParameter
import com.example.mycomposecookbook.util.extension.imageLoader

@Composable
@Preview
fun UserProfile(@PreviewParameter(UserPreviewParameter::class, limit = 1) user: User) {
    Scaffold(topBar = {
        Text(text = user.firstName, fontSize = 20.sp)
    }) {

        Row {
            AsyncImage(
                model = LocalContext.current.imageLoader(user.avatar, R.drawable.jetpack),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp),
            )
        }
    }

}
