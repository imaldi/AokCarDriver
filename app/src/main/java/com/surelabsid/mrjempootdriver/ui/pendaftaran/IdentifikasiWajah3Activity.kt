package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.net.Uri
import android.os.Bundle
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityIdentifikasiWajah2Binding
import com.surelabsid.mrjempootdriver.databinding.ActivityIdentifikasiWajah3Binding
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

@AndroidEntryPoint
class IdentifikasiWajah3Activity : AppFaceDetection() {

    private lateinit var binding: ActivityIdentifikasiWajah3Binding

    private var countDetect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentifikasiWajah3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        startCamera(binding.viewFinder.surfaceProvider)

        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
        }

    }

    fun startCamera(surfaceProvider: Preview.SurfaceProvider) {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider
            try{
                cameraProvider = cameraProviderFeature.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(surfaceProvider)

                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(480, 640))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                    .setImageQueueDepth(1)
                    .build()

                imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), { imageProxy ->
                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                    // insert your code here.
                    imageProxyValue = imageProxy
                    mediaImage = imageProxyValue.image!!
                    val bitmap = mediaImage.toBitmap()
                    val rotatedBitmap = bitmap.rotateAndCrop(-90f)
                    // Pass image to an ML Kit Vision API
                    image = InputImage.fromBitmap(rotatedBitmap, 0)
                    executeFaceDetection()
                })

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview)
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun executeFaceDetection() {
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)

        if (countDetect > 1) {
            moveActivity(IdentifikasiWajah4Activity::class.java)
        }

        if (image != null) {
            val result = detector.process(image!!)
                .addOnSuccessListener { faces ->
                    // Task completed successfully
                    if (faces.size == 0) {

//                        showToast(this, getString(R.string.face_not_found))
                        binding.textResult.text = getString(R.string.face_not_found)

                    } else {
                        // if the eye open probability is less than 0.4, the person is blinking
                        for (face in faces) {

                            if (face.leftEyeOpenProbability ?: 0f < 0.4 || face.rightEyeOpenProbability ?: 0f < 0.4) {
                                countDetect++
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
//                    showToast(this, e.message)
                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxyValue.close()
                }
        }
    }

    override fun onStop() {
        super.onStop()
        cameraExecutor.shutdown()
    }
}