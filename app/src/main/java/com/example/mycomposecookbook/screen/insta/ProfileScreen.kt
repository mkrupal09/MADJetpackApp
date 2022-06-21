package com.example.mycomposecookbook.screen.insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.model.UserPreviewParameter
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme

class ProfileScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeCookBookTheme {


            }
        }
    }

    @Composable
    @Preview
    fun ProfileUi(@PreviewParameter(UserPreviewParameter::class) user: User) {

        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row {
                Column {
                    AsyncImage(
                        model = user.avatar,
                        placeholder = rememberAsyncImagePainter(model = R.drawable.jetpack),
                        contentDescription = "Image",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(Color.White)
                            .border(2.dp, color = Color.White),
                        contentScale = ContentScale.Crop,
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                ) {
                    Stats(title = "Posts", value = "15", modifier = Modifier.weight(1f))
                    Stats(title = "Followers", value = "15", modifier = Modifier.weight(1f))
                    Stats(title = "Followings", value = "15", modifier = Modifier.weight(1f))
                }

            }
            Text(
                text = user.firstName + user.lastName,
                fontSize = 20.sp,
                color = Color.White
            )

            Text(text = "Story highlights", color = Color.White)
            Text(text = "Keep your favorite stories on your profile", color = Color.White)

            Stories(list = arrayListOf("1", "2", "3"), modifier = Modifier.padding(top = 10.dp))
        }
    }

    @Composable
    fun Stats(title: String, value: String, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = value, fontSize = 20.sp, color = Color.White)
            Text(text = title, color = Color.White)
        }
    }

    @Composable
    fun Stories(list: ArrayList<String> = arrayListOf("1", "2", "3"), modifier: Modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
        ) {
            items(list) { item ->

                AsyncImage(
                    model = item, contentDescription = "image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, Color.White)
                )
            }
        }
    }
}