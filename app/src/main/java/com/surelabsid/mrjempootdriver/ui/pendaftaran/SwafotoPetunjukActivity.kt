package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivitySwafotoPetunjukBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class SwafotoPetunjukActivity : BaseActivity() {

    lateinit var binding: ActivitySwafotoPetunjukBinding

    private var image_path: String? = null

    private var mime_type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwafotoPetunjukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionCamera()
        initView()


    }

    private fun initView() {

        binding.buttonRetake.setOnClickListener {
//            openCamera()
            val intent = Intent(this, SwafotoUploadActivity::class.java)
            startActivityForResult(intent, CAMERA_CODE)
        }

        binding.buttonUploadFoto.setOnClickListener {
//            openCamera()
            val intent = Intent(this, SwafotoUploadActivity::class.java)
            startActivityForResult(intent, CAMERA_CODE)
        }

        binding.imageButtonBack.setOnClickListener {
            this.onBackPressed()
        }

        binding.buttonSelanjutnya.setOnClickListener {
            openActivity(PengenalanWajahActivity::class.java)
        }
    }

    private fun viewDraft() {
        if (sessionManager.ktp_photo_selfie != "") {
            with(binding) {
                textLabel.text = getString(R.string.label_review_foto_selfie)
                layoutReview.visibility = View.VISIBLE
                layoutUpload.visibility = View.INVISIBLE
                buttonUploadFoto.visibility = View.GONE

            }
        } else {
            with(binding) {
                textLabel.text = getString(R.string.label_upload_foto_selfie)
                layoutUpload.visibility = View.VISIBLE
                layoutReview.visibility = View.INVISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_CODE) {
//            initCamera(data)
            viewImage(data)
            viewDraft()
        }
    }

    private fun viewImage(data: Intent?) {
        image_path = data?.getStringExtra("swafoto_photo_path").toString()
        sessionManager.ktp_photo_selfie = image_path.toString()
        val bitmapOption = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(image_path, bitmapOption)
        binding.imageSwaFoto.setImageBitmap(bitmap)
    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "SwaFoto$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageSwaFoto.setImageBitmap(BitmapFactory.decodeFile(image_path))

            //save draft
            sessionManager.ktp_photo_selfie = image_path ?: ""

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }


}