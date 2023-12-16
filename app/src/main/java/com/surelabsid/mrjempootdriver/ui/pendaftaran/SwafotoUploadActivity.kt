package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivitySwafotoUploadBinding
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.surelabsid.mrjempootdriver.utils.initPermissionCamera
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class SwafotoUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwafotoUploadBinding

    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private var imageCapture: ImageCapture? = null
    private var cameraExecutor: ExecutorService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwafotoUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionCamera()

        cameraExecutor = Executors.newSingleThreadExecutor()

        initView()
    }

    private fun initView() {
        startCamera()

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            takePhoto.setOnClickListener {
                takePhoto()
            }
        }
    }

    private fun takePhoto() {
        val file = File(getOutputDirectory(), SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()


        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Intent(this@SwafotoUploadActivity, SwafotoReviewActivity::class.java).apply {
                        this.putExtra("swafoto_photo_path", file.absolutePath)
                        setResult(CAMERA_CODE, this)
                        finish()
                    }

                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    showToast(this@SwafotoUploadActivity, exception.toString())
                }

            })
    }

    private fun getOutputDirectory(): File? {
        val mediaDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "Swafoto")
        mediaDir.mkdirs()
        return if (mediaDir.exists()) mediaDir else filesDir
    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider
            try{
                cameraProvider = cameraProviderFeature.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

                imageCapture = ImageCapture.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor?.shutdown()
    }


}