package com.example.mycomposecookbook.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.screen.auth.MainActivity
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import com.example.mycomposecookbook.ui.theme.MyFontFamily
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class IntoActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiController = rememberSystemUiController()
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()


            MyComposeCookBookTheme {
                Box {
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        count = 10,
                        state = pagerState
                    ) {
                        val backgroundColor =
                            if (currentPage % 2 == 0) MaterialTheme.colors.primary else Color.Gray
                        uiController.setStatusBarColor(backgroundColor)
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                        ) {
                            Column(
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = R.drawable.jetpack),
                                    contentDescription = "Image",
                                    modifier = Modifier.size(280.dp)
                                )
                                Text(
                                    text = "Welcome to JetMad",
                                    fontSize = 22.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    fontFamily = MyFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color=Color.White
                                )
                            }
                        }
                    }

                    HorizontalPagerIndicator(
                        indicatorHeight = 5.dp,
                        indicatorWidth = 25.dp,
                        indicatorShape = RoundedCornerShape(5.dp),
                        inactiveColor = Color.Black,
                        activeColor = Color.White,
                        spacing = 10.dp,
                        pagerState = pagerState, modifier = Modifier
                            .align(
                                Alignment.BottomCenter
                            )
                            .padding(10.dp)
                    )

                    NumberIndicator(
                        totalItem = pagerState.pageCount,
                        currentIndex = pagerState.currentPage,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp), callback = { index ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )


                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(if (pagerState.currentPage == 0) pagerState.pageCount - 1 else pagerState.currentPage - 1)
                                }
                            }
                            .align(Alignment.CenterStart)
                            .padding(5.dp)
                    )

                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(if (pagerState.currentPage == pagerState.pageCount - 1) 0 else pagerState.currentPage + 1)
                                }
                            }
                            .align(Alignment.CenterEnd)
                            .padding(5.dp)
                    )

                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        Button(
                            onClick = {
                                openNextScreen()
                            }, modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                        ) {
                            Text(text = "Get Started")
                        }

                    } else {
                        Button(
                            onClick = {
                                openNextScreen()
                            }, modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                        ) {
                            Text(text = "Skip")
                        }
                    }
                }

            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun openNextScreen() {
        startActivity(Intent(this@IntoActivity, MainActivity::class.java))
        finish()
    }

    @Composable
    fun NumberIndicator(
        totalItem: Int,
        currentIndex: Int,
        modifier: Modifier = Modifier,
        callback: (Int) -> Unit
    ) {

        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            items(totalItem) { index ->

                Box(
                    modifier = Modifier
                        .size(if (currentIndex == index) 35.dp else 25.dp)
                        .run {
                            background(
                                shape = CircleShape,
                                color = if (currentIndex == index) Color.Black else Color.White
                            )
                        }
                        .clickable {
                            callback(index)
                        }

                ) {
                    Text(
                        text = (index + 1).toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = if (currentIndex == index) Color.White else Color.Black
                    )
                }
            }
        }
    }
}