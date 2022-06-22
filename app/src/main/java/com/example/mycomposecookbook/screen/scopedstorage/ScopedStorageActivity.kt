package com.example.mycomposecookbook.screen.scopedstorage

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.MimeTypeMap
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.screen.base.BaseComponentActivity
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*

class ScopedStorageActivity : BaseComponentActivity() {

    private val selectedImage = mutableStateOf("")

    //Image picker from camera | No write or read permission needed
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeCookBookTheme(darkTheme = false) {
                Content()
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    fun Content() {
        val bottomSheetState =
            rememberBottomSheetState(BottomSheetValue.Collapsed, animationSpec = spring())

        val scaffoldState =
            rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

        val coroutineScope = rememberCoroutineScope()

        BottomSheetScaffold(
            sheetContent = {
                ImagePickerBottomSheet(coroutineScope, scaffoldState)
            },
            sheetPeekHeight = 0.dp,
            sheetElevation = 15.dp,
            sheetShape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
            scaffoldState = scaffoldState
        ) {
            ProfileUi(coroutineScope, bottomSheetState)
        }

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    private fun ProfileUi(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        bottomSheetScaffoldState: BottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    ) {
        val imageUrl = remember { selectedImage }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                AsyncImage(
                    placeholder = painterResource(id = R.drawable.profile_placeholder),
                    error = painterResource(id = R.drawable.profile_placeholder),
                    model = imageUrl.value,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .clickable {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.expand()
                            }
                        }
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Uri:" + selectedImage.value + "\nType : " + Uri.parse(selectedImage.value)
                        .getMimeType(this@ScopedStorageActivity)
                )

                Text(
                    text = "Andrew Joseph",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(15.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ImagePickerBottomSheet(
        coroutineScope: CoroutineScope,
        scaffoldState: BottomSheetScaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fun dismissBottomSheet() {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.collapse()
                }
            }

            Button(onClick = {
                dismissBottomSheet()
                pickImageUsingSAF()
            }) {
                Text(text = "Pick image using SAF")
            }

            Button(onClick = {
                dismissBottomSheet()
                pickImageFromCamera()
            }) {
                Text(text = "Pick image from camera")
            }

            Button(onClick = {
                dismissBottomSheet()
                pickImageFromMediaStore()
            }) {
                Text(text = "Pick image using MediaStore")
            }

            Button(onClick = {
                dismissBottomSheet()
                saveImageToMediaStore(imageUri)
            }) {
                Text(text = "Save to shared storage")
            }

            Button(onClick = {
                dismissBottomSheet()
                saveFileUsingSAF()
            }) {
                Text(text = "Save pdf file using saf")
            }

            Button(onClick = {
                dismissBottomSheet()
                cropImage()
            }) {
                Text(text = "Crop Image")
            }

            Button(onClick = {
                share()
            }) {
                Text(text = "Share")
            }
        }
    }

    private fun pickImageUsingSAF() {

    }

    private fun pickImageFromCamera() {

    }

    private fun pickImageFromMediaStore() {

    }

    private fun saveImageToMediaStore(
        currentUri: Uri,
        filename: String = "screenshot.jpg",
        mimeType: String = "image/jpeg",
        directory: String = Environment.DIRECTORY_PICTURES + "/ScopedStorage",
        mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ) {

    }

    private fun saveFileUsingSAF() {

    }

    private fun cropImage() {

    }

    private fun share() {

    }



    @Throws(IOException::class)
    fun copy(input: InputStream?, output: OutputStream?): Int {
        val count = copyLarge(input!!, output!!)
        return if (count > Int.MAX_VALUE) {
            -1
        } else count.toInt()
    }


    @Throws(IOException::class)
    fun copyLarge(input: InputStream, output: OutputStream): Long {
        val DEFAULT_BUFFER_SIZE = 1024 * 4
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var count: Long = 0
        var n: Int
        while (-1 != input.read(buffer).also { n = it }) {
            output.write(buffer, 0, n)
            count += n.toLong()
        }
        return count
    }



    fun Uri.getMimeType(context: Context): String? {
        return when (scheme) {
            ContentResolver.SCHEME_CONTENT -> context.contentResolver.getType(this)
            ContentResolver.SCHEME_FILE -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(toString()).lowercase()
            )
            else -> null
        }
    }




    suspend fun InputStream.convertMultiPart(): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "pic", "myPic", RequestBody.create(
                "image/*".toMediaType(),
                this.readBytes()
            )
        )
    }
}