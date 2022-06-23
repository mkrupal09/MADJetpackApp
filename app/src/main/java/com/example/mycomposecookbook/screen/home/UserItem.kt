package com.example.mycomposecookbook.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
fun UserItem(
    @PreviewParameter(UserPreviewParameter::class) user: User,
    modifier: Modifier = Modifier
) {
    Card(elevation = 5.dp, modifier = modifier) {
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