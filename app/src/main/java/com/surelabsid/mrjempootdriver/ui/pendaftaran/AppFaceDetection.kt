package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.media.Image
import android.os.*
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
open class AppFaceDetection : AppCompatActivity() {

    val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    var image: InputImage? = null
    lateinit var imageProxyValue: ImageProxy
    lateinit var mediaImage: Image

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initPermissionCamera()

    }

    fun getOutputDirectory(): File? {
        val mediaDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "Swafoto")
        mediaDir.mkdirs()
        return if (mediaDir.exists()) mediaDir else filesDir
    }

}