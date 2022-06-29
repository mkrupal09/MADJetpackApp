package com.example.mycomposecookbook.screen.player

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import com.example.mycomposecookbook.ui.theme.MyFontFamily
import java.util.*
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() {

    private val mediaPlayer = MediaPlayer()

    private val playState = mutableStateOf(false)
    private val currentPlayPosition = mutableStateOf(0)
    private val maxPlayPosition = mutableStateOf(1)
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeCookBookTheme {
                Scaffold(topBar = {
                    Toolbar()
                }) { paddingValues ->
                    val x = paddingValues
                    Player()
                }

                DisposableEffect(key1 = Unit) {
                    onDispose {
                        mediaPlayer.stop()
                        timer.cancel()
                    }
                }
            }
        }
    }

    private fun startTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                currentPlayPosition.value = mediaPlayer.currentPosition
            }
        }, 1000, 1000)
    }

    @Composable
    private fun Toolbar() {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                playMedia(context, it)
                startTimer()
            }
        )

        Row(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Music player",
                modifier = Modifier.weight(1f),
                fontFamily = MyFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Open", modifier = Modifier.clickable {
                launcher.launch(pickImageUsingSAF())
            }, fontFamily = MyFontFamily, fontWeight = FontWeight.Normal)
        }
    }

    private fun playMedia(context: Context, it: ActivityResult) {
        mediaPlayer.setDataSource(context, it.data!!.data!!)
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.setOnPreparedListener {
            maxPlayPosition.value = mediaPlayer.duration
            playState.value = true
        }
    }

    @Composable
    private fun Player() {
        val progress = remember {
            currentPlayPosition
        }
        val playingStatus = remember { playState }
        val totalDuration = remember {
            maxPlayPosition
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Artwork("https://picsum.photos/300/300")
            PlayerControls(
                progress,
                playingStatus,
                totalDuration.value
            )
        }
    }

    private fun formatToDigitalClock(miliSeconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(miliSeconds).toInt() % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds).toInt() % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds).toInt() % 60
        return when {
            hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
            seconds > 0 -> String.format("00:%02d", seconds)
            else -> {
                "00:00"
            }
        }
    }

    @Composable
    private fun PlayerControls(
        currentPlayTime: MutableState<Int>,
        playingState: MutableState<Boolean>,
        totalDuration: Int
    ) {
        val percentage = (currentPlayTime.value * 100) / totalDuration
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = formatToDigitalClock(currentPlayTime.value.toLong()))
                Slider(
                    value = percentage.toFloat(),
                    onValueChange = {

                    }, modifier = Modifier
                        .weight(1f)
                        .padding(15.dp),
                    valueRange = 0f..100f
                )
                Text(text = formatToDigitalClock(totalDuration.toLong()))
            }

            Icon(
                if (playingState.value) painterResource(id = R.drawable.ic_pause) else painterResource(
                    id = R.drawable.ic_play
                ),
                contentDescription = "play/pause",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                            playState.value = false
                        } else {
                            mediaPlayer.start()
                            playState.value = true
                        }
                    }
            )
        }
    }

    @Composable
    private fun Artwork(image: String = "") {
        AsyncImage(
            model = image, contentDescription = "image",
            modifier = Modifier
                .aspectRatio(1.1f)
                .padding(10.dp),
            contentScale = ContentScale.Crop
        )
    }

    private fun pickImageUsingSAF(): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "audio/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        return intent
    }
}