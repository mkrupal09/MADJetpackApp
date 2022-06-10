package com.example.mycomposecookbook.screen.scopedstorage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.mycomposecookbook.R
import com.example.mycomposecookbook.screen.base.BaseComponentActivity
import com.example.mycomposecookbook.ui.theme.MyComposeCookBookTheme
import kotlinx.coroutines.launch
import java.io.File

class ScopedStorageActivity : BaseComponentActivity() {


    private val selectedImage = mutableStateOf("")
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
        val imageUrl = remember { selectedImage }

        val bottomSheetScaffoldState =
            rememberBottomSheetState(BottomSheetValue.Collapsed, animationSpec = spring())

        val scaffoldState =
            rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetScaffoldState)

        val coroutineScope = rememberCoroutineScope()

        BottomSheetScaffold(
            sheetContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        pickImageUsingSAF()
                    }) {
                        Text(text = "Pick image using SAF")
                    }

                    Button(onClick = {
                        pickImageFromCamera()
                    }) {
                        Text(text = "Pick image from camera")
                    }

                    Button(onClick = {
                        pickImageFromMediaStore()
                    }) {
                        Text(text = "Pick image using MediaStore")
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            sheetElevation = 15.dp,
            sheetShape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
            scaffoldState = scaffoldState
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
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
                        .clickable {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.expand()
                            }
                        }
                )
            }
        }

    }

    private fun pickImageUsingSAF() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"

        //If you not specify all mimetype will be taken
        val mimeTypes = arrayOf(
            "image/png",
            "image/jpg",
            "image/jpeg"
        )
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        safLauncher.launch(intent)
    }

    private fun pickImageFromCamera() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
        cameraLauncher.launch(takePhotoIntent)
    }


    private val mediaStoreLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            selectedImage.value = it.data?.getStringExtra("media") ?: ""
        }


    private fun pickImageFromMediaStore() {
        mediaStoreLauncher.launch(
            Intent(
                this@ScopedStorageActivity,
                MediaSelectionActivity::class.java
            )
        )
    }


    //Image picker from camera
    //No write or read permission needed
    private lateinit var imageUri: Uri
    private var imgPath: String = ""

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            selectedImage.value = imgPath
        }


    private fun setImageUri(): Uri {
        /*val dir =
            getExternalFilesDir(null)!! // Android>Data>(packagename)>files>(Your file saved here)*/
        /* val dir =
             getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) // Android>Data>(packagename)>files>DCIM>(Your file saved here)*/


        //val dir = externalCacheDir!! // Android>Data>(packagename)>cache>(Your file saved here)*/

        val dir = filesDir


        val folder = File(dir.absolutePath)
        folder.mkdirs()

        val file = File(folder, "${System.currentTimeMillis()}.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            file
        )

        imgPath = file.absolutePath
        return imageUri
    }

    private val safLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val uri = it?.data?.data
            if (uri != null) {
                /**
                 * Take a persistable URI permission grant that has been offered. Once
                 * taken, the permission grant will be remembered across device reboots.
                 */
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                selectedImage.value = uri.toString()
            } else {
                /*setError(R.string.error_failed_pick_gallery_image)*/
            }
        }
}