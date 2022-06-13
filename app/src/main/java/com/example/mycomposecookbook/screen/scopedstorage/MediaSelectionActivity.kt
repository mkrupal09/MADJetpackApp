package com.example.mycomposecookbook.screen.scopedstorage


import android.content.ContentUris
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaSelectionActivity : ComponentActivity() {

    private val mediaList = mutableStateListOf<ImageModel>()
    private val showLoading = mutableStateOf(false)
    private val selectedIndex = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeCookBookTheme {
                Scaffold(backgroundColor = Color.White,
                    topBar = {
                        Row(
                            modifier = Modifier.height(52.dp),
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "back",
                                modifier = Modifier
                                    .clickable {
                                        finish()
                                    }
                                    .align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "Select Image",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        Content()
                    }
                }
            }
        }

    }

    @Composable
    fun Content() {
        val mediaListRemember = remember {
            mediaList
        }
        val showLoadingRemember = remember {
            showLoading
        }
        val selectedIndexRemember = remember {
            selectedIndex
        }
        Box(modifier = Modifier.fillMaxSize()) {
            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    itemsIndexed(mediaListRemember) { index, model ->
                        Box(modifier = Modifier.aspectRatio(1.1f).run {
                            if (selectedIndexRemember.value == index) {
                                border(5.dp, MaterialTheme.colors.primary, shape = RectangleShape)
                            } else {
                                this
                            }
                        }) {
                            AsyncImage(
                                model = model.uri,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        /* val sel = model.isSelected.not()*/
                                        selectedIndex.value = index
                                        /* mediaList[index] = model.copy(isSelected = sel)*/
                                    }
                            )
                            Checkbox(checked = model.isSelected,
                                onCheckedChange = {
                                    selectedIndex.value = index
                                    /*mediaList[index] = model.copy(isSelected = it)*/
                                })
                        }
                    }
                })

            if (showLoadingRemember.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
            if (selectedIndexRemember.value >= 0) {
                Button(
                    onClick = {
                        val intentX = Intent()
                        intentX.putExtra("media", mediaList[selectedIndex.value].uri.toString())
                        setResult(RESULT_OK, intentX)
                        finish()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            showLoading.value = true
            lifecycleScope.launch(Dispatchers.IO) {
                val images = queryImageStorage()
                withContext(Dispatchers.Main) {
                    mediaList.clear()
                    mediaList.addAll(images)
                    Log.e("images", images.toString())
                    showLoading.value = false
                }
            }
        }
    }

    private suspend fun queryImageStorage() = withContext(Dispatchers.IO) {
        val list = arrayListOf<ImageModel>()
        val imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media._ID
        )
        val imageSortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    list.add(ImageModel(contentUri, contentUri.path.toString()))
                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }
        list
    }
}