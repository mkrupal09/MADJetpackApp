package com.example.mycomposecookbook.screen.scopedstorage

import android.Manifest
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*

class ScopedStorageActivity : BaseComponentActivity() {


    private val selectedImage = mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MyComposeCookBookTheme(darkTheme = false) {
                Content()
            }
        }
/*
        val dir = Environment.getExternalStoragePublicDirectory()

        getExternalFilesDir()
        val newDir = File(dir, "myappx")
        if (newDir.exists().not()) {
            newDir.mkdir()
        }*/


        /*saveFileUsingSAF()*/

        /*requestPermissions(arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),101)*/
    }

    suspend fun fun1(): String {
        delay(2000)
        return "fun 1"
    }

    suspend fun fun2(): String {
        delay(1000)
        return "fun 2"
    }

    suspend fun fun3(): String {
        delay(100)
        return "fun 3"
    }

    fun openSettingManageExternalStorage() {
        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        startActivity(intent)
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
            fun onAction() {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.collapse()
                }
            }

            Button(onClick = {
                onAction()
                pickImageUsingSAF()
            }) {
                Text(text = "Pick image using SAF")
            }

            Button(onClick = {
                onAction()
                pickImageFromCamera()
            }) {
                Text(text = "Pick image from camera")
            }

            Button(onClick = {
                onAction()
                pickImageFromMediaStore()
            }) {
                Text(text = "Pick image using MediaStore")
            }

            Button(onClick = {
                saveImageToMediaStore(imageUri)
            }) {
                Text(text = "Save to shared storage")
            }

            Button(onClick = {
                saveFileUsingSAF()
            }) {
                Text(text = "Save pdf file using saf")
            }
        }
    }

    private fun pickImageUsingSAF() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //intent.type = "image/*"
        intent.type = "*/*"

        //If you not specify all mimetype will be taken
        //val mimeTypes = arrayOf(
        //    "image/png",
        //   "image/jpg",
        //   "image/jpeg"
        // )
        //intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        safLauncher.launch(intent)
    }

    private fun pickImageFromCamera() {

        val dir =
            getExternalFilesDir(Environment.DIRECTORY_MOVIES)!! // Android>Data>(packagename)>files>(Your file saved here)*/
        /* val dir =
             getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) // Android>Data>(packagename)>files>DCIM>(Your file saved here)*/


        //val dir = externalCacheDir!! // Android>Data>(packagename)>cache>(Your file saved here)*/

        //val dir = externalCacheDir


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

        /*imgPath = file.absolutePath*/

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraLauncher.launch(takePhotoIntent)
    }


    private val mediaStoreLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            selectedImage.value = it.data?.getStringExtra("media") ?: ""
        }

    private val mediaStorePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                mediaStoreLauncher.launch(
                    Intent(
                        this@ScopedStorageActivity,
                        MediaSelectionActivity::class.java
                    )
                )
            }
        }

    private fun pickImageFromMediaStore() {

        //It will display images which created by own app
        /*mediaStoreLauncher.launch(
            Intent(
                this@ScopedStorageActivity,
                MediaSelectionActivity::class.java
            )
        )*/

        //If you want to display all shared images then it requires permission
        mediaStorePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    //Image picker from camera
    //No write or read permission needed
    private lateinit var imageUri: Uri
    /*private var imgPath: String = ""*/

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            selectedImage.value = imageUri.toString()
        }


    private val safLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val uri = it?.data?.data
            if (uri != null) {
                /**
                 * Take a persistable URI permission grant that has been offered. Once
                 * taken, the permission grant will be remembered across device reboots.
                 */
                /*contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )*/
                selectedImage.value = uri.toString()
            } else {
                /*setError(R.string.error_failed_pick_gallery_image)*/
            }
        }


    @Suppress("DEPRECATION")
    private fun saveImageToMediaStore(
        currentUri: Uri,
        filename: String = "screenshot.jpg",
        mimeType: String = "image/jpeg",
        directory: String = Environment.DIRECTORY_PICTURES + "/ScopedStorage",
        mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ) {
        if (File(directory).exists().not()) {
            File(directory).mkdir()
        }
        val imageOutStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, directory)
            }

            contentResolver.run {
                val uri =
                    contentResolver.insert(mediaContentUri, values)
                        ?: return
                imageOutStream = openOutputStream(uri) ?: return
            }
        } else {
            val imagePath = Environment.getExternalStoragePublicDirectory(directory).absolutePath
            val image = File(imagePath, filename)
            imageOutStream = FileOutputStream(image)
        }


        /*imageOutStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }*/

        val inputStream = contentResolver.openInputStream(currentUri)
        copy(inputStream, imageOutStream)

    }


    @Throws(IOException::class)
    fun copy(input: InputStream?, output: OutputStream?): Int {
        val count = copyLarge(input!!, output!!)
        return if (count > Int.MAX_VALUE) {
            -1
        } else count.toInt()
    }


    private val DEFAULT_BUFFER_SIZE = 1024 * 4

    @Throws(IOException::class)
    fun copyLarge(input: InputStream, output: OutputStream): Long {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var count: Long = 0
        var n: Int
        while (-1 != input.read(buffer).also { n = it }) {
            output.write(buffer, 0, n)
            count += n.toLong()
        }
        return count
    }


    private val saveFileUsingSAFLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            contentResolver.openOutputStream(it.data?.data!!).use { it ->
                //it?.write("teststring here".toByteArray(Charsets.UTF_8))
                copyLarge(assets.open("Obs.pdf"), it!!)
            }

        }

    private fun saveFileUsingSAF() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            //type = "text/plain"
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "Obs.pdf")
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
        }
        saveFileUsingSAFLauncher.launch(intent)
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
}