package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.graphics.*
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.BoringLayout
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface.ROTATION_0
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityIdentifikasiWajah1Binding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class IdentifikasiWajah1Activity : AppFaceDetection() {

    private lateinit var binding: ActivityIdentifikasiWajah1Binding

    private var countDetect = 0

    private var identifikasi_wajah_1 = false
    private var identifikasi_wajah_2 = false
    private var identifikasi_wajah_3 = false
    private var identifikasi_wajah_4 = false

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentifikasiWajah1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setViewIdentifikasiWajah()

    }

    private fun initView() {

        startCamera(binding.viewFinder.surfaceProvider)

        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
        }

    }

    private fun setViewIdentifikasiWajah() {
        with(binding) {
            if (!identifikasi_wajah_1 && !identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
                layoutScreen1.visibility = View.VISIBLE
                layoutScreen2.visibility = View.INVISIBLE
                layoutScreen3.visibility = View.INVISIBLE
                layoutScreen4.visibility = View.INVISIBLE
            } else if (identifikasi_wajah_1 && !identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
                layoutScreen1.visibility = View.INVISIBLE
                layoutScreen2.visibility = View.VISIBLE
                layoutScreen3.visibility = View.INVISIBLE
                layoutScreen4.visibility = View.INVISIBLE
            } else if (identifikasi_wajah_1 && identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
                layoutScreen1.visibility = View.INVISIBLE
                layoutScreen2.visibility = View.INVISIBLE
                layoutScreen3.visibility = View.VISIBLE
                layoutScreen4.visibility = View.INVISIBLE
            } else if (identifikasi_wajah_1 && identifikasi_wajah_2 && identifikasi_wajah_3 && !identifikasi_wajah_4) {
                layoutScreen1.visibility = View.INVISIBLE
                layoutScreen2.visibility = View.INVISIBLE
                layoutScreen3.visibility = View.INVISIBLE
                layoutScreen4.visibility = View.VISIBLE
            } else if (identifikasi_wajah_1 && identifikasi_wajah_2 && identifikasi_wajah_3 && identifikasi_wajah_4) {
                moveActivity(IdentifikasiWajah5Activity::class.java)
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

                imageAnalysis.setAnalyzer(cameraExecutor, { imageProxy ->
                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                    // insert your code here.
                    imageProxyValue = imageProxy
                    mediaImage = imageProxyValue.image!!
                    val bitmap = mediaImage.toBitmap()
                    var rotation_landscape = true
                    if (bitmap.width < bitmap.height) {
                        rotation_landscape = false
                    }

                    var rotatedBitmap = bitmap
                    if (rotation_landscape) {
                        rotatedBitmap = bitmap.rotateAndCrop(-90f)
                    } else {
                        rotatedBitmap = bitmap.rotateAndCrop(0f)
                    }

                    // Pass image to an ML Kit Vision API
                    image = InputImage.fromBitmap(rotatedBitmap, 0)

                    if (!identifikasi_wajah_1 && !identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
//                        runOnUiThread {
//                            binding.imageView.setImageBitmap(rotatedBitmap)
//                        }
                        executeFaceDetection()
                    } else if (identifikasi_wajah_1 && !identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
//                        runOnUiThread {
//                            binding.imageView.setImageBitmap(rotatedBitmap)
//                        }

                        executeFaceDetection2()
                    } else if (identifikasi_wajah_1 && identifikasi_wajah_2 && !identifikasi_wajah_3 && !identifikasi_wajah_4) {
                        executeFaceDetection3()
                    } else if (identifikasi_wajah_1 && identifikasi_wajah_2 && identifikasi_wajah_3 && !identifikasi_wajah_4) {
                        executeFaceDetection4()
                    }

                })

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview)
            }catch (e: Throwable){
//                e.printStackTrace()
                showToast(this, "Try catch message : ${e.message}")
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

        if (image != null) {
            val result = detector.process(image!!)
                .addOnSuccessListener { faces ->
                    // Task completed successfully
                    if (faces.size == 0) {

                        binding.textResult.text = getString(R.string.face_not_found)

                    } else {
                        identifikasi_wajah_1 = true
                        setViewIdentifikasiWajah()
                    }
                }
                .addOnFailureListener { e ->

                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxyValue.close()
                }
        }
    }

    private fun executeFaceDetection2() {
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)

        if (image != null) {
            val result = detector.process(image!!)
                .addOnSuccessListener { faces ->
                    // Task completed successfully

                    showToast(this, "Face : ${faces.size}")

                    if (faces.size == 0) {
                        binding.textResult.text = getString(R.string.face_not_found)
//                        showToast(this, getString(R.string.face_not_found))

                    } else {
                        for (face in faces){
                            val mouth = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)

                            showToast(this, "position y : ${mouth?.position?.y}")

                            if (mouth != null) {
                                if (mouth.position.y <= 10) {
                                    binding.textResult.text = "Buka mulut anda"
                                } else if (mouth.position.y <= 100) {
                                    binding.textResult.text = "Buka mulut anda lebih lebar"
                                } else {
                                    identifikasi_wajah_2 = true
                                    setViewIdentifikasiWajah()
                                }
                            }

                        }
                    }
                }
                .addOnFailureListener { e ->
                    showToast(this, e.message)
                    mediaImage.close()
                    imageProxyValue.close()
                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxyValue.close()
                }
        } else {
            showToast(this, "Face y : $image")
        }

    }

    private fun executeFaceDetection3() {
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)

        if (image != null) {
            val result = detector.process(image!!)
                .addOnSuccessListener { faces ->
                    // Task completed successfully
                    if (faces.size == 0) {
                        binding.textResult.text = getString(R.string.face_not_found)
                    } else {
                        // if the eye open probability is less than 0.4, the person is blinking
                        for (face in faces) {

                            if (face.leftEyeOpenProbability ?: 0f < 0.4 || face.rightEyeOpenProbability ?: 0f < 0.4) {
                                identifikasi_wajah_3 = true
                                setViewIdentifikasiWajah()
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->

                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxyValue.close()
                }
        }
    }

    private fun executeFaceDetection4() {
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)

        if (image != null) {
            val result = detector.process(image!!)
                .addOnSuccessListener { faces ->
                    // Task completed successfully
                    if (faces.size == 0) {
                        binding.textResult.text = getString(R.string.face_not_found)
                    } else {
                        for (face in faces) {
                            val right = face.headEulerAngleY
                            if (right < -10) {
                                identifikasi_wajah_4 = true
                                setViewIdentifikasiWajah()
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->

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