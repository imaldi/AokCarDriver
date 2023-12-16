package com.surelabsid.mrjempootdriver.ui.verifikasikesehatan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.surelabsid.mrjempootdriver.databinding.ActivityUploadSuratBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.DOCUMENT_BEBAS_COVID_RESULT
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class UploadSuratActivity : BaseActivity() {

    private lateinit var binding: ActivityUploadSuratBinding

    private var image_path: String? = null

    private var mime_type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadSuratBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Upload Surat"
        binding.toolbar.backButton.setOnClickListener {
            onBackPressed()
        }

        initPermissionCamera()

        initView()

    }

    private fun initView() {

        openCamera()

        binding.buttonRetake.setOnClickListener {
            openCamera()
        }

        binding.buttonSelanjutnya.setOnClickListener {
            val intent = Intent(this, VerifikasiKesahatanActivity::class.java)
            intent.putExtra("document_bebas_covid", image_path)
            setResult(DOCUMENT_BEBAS_COVID_RESULT, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.Companion.code_request.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
        }
    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "DocumentBebasCovid$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageView.setImageBitmap(BitmapFactory.decodeFile(image_path))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }


}