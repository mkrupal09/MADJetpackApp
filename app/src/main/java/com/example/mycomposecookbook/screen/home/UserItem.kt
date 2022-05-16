package com.example.mycomposecookbook.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun UserItem(@PreviewParameter(UserPreviewParameter::class) user: User) {
    Card(elevation = 5.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                model = LocalContext.current.imageLoader(user.avatar, R.drawable.jetpack),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(text = "${user.firstName}  ${user.lastName}", fontSize = 22.sp)
                Text(text = user.email)
            }
        }
    }
}