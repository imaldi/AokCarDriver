package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Surface
import android.view.SurfaceControl
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranUploadKtpactivityBinding
import com.surelabsid.mrjempootdriver.databinding.DialogAmbilFotoKtpBinding
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.initPermissionCamera
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class PendaftaranUploadKTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendaftaranUploadKtpactivityBinding

    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private var imageCapture: ImageCapture? = null
    private var cameraExecutor: ExecutorService? = null

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var dialogAmbilFotoKtp: DialogAmbilFotoKtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranUploadKtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionCamera()

        cameraExecutor = Executors.newSingleThreadExecutor()

        showBottomSheet()

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

    private fun showBottomSheet(){
        val bottomSheet = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider
            try{
                cameraProvider = cameraProviderFeature.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                preview.targetRotation = Surface.ROTATION_180

                imageCapture = ImageCapture.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val file = File(getOutputDirectory(), SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()


        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Intent(this@PendaftaranUploadKTPActivity, SwafotoReviewActivity::class.java).apply {
                        this.putExtra("photo_ktp_path", file.absolutePath)
                        setResult(Constant.Companion.code_request.CAMERA_CODE, this)
                        finish()
                    }

                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    showToast(this@PendaftaranUploadKTPActivity, exception.toString())
                }

            })
    }

    private fun getOutputDirectory(): File? {
        val mediaDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "Swafoto")
        mediaDir.mkdirs()
        return if (mediaDir.exists()) mediaDir else filesDir
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor?.shutdown()
    }

}