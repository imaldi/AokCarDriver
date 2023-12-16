package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranFotoKtpBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PendaftaranFotoKTPActivity : BaseActivity() {

    lateinit var binding: ActivityPendaftaranFotoKtpBinding

    lateinit var viewModel: PendaftaranViewModel

    private var image_path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranFotoKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            register_driver.observe(this@PendaftaranFotoKTPActivity, { responseRegister(it) })
            loading.observe(this@PendaftaranFotoKTPActivity, { isLoading(it) })
            error.observe(this@PendaftaranFotoKTPActivity, { showError(this@PendaftaranFotoKTPActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                buttonSelanjutnya.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                buttonSelanjutnya.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }


    private fun responseRegister(it: ResponseRegisterDriver?) {
        if (it?.code == "200") {
            openActivity(SwafotoPetunjukActivity::class.java)
        } else if (it?.code == "201") {
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_LONG)
        }
    }

    private fun initView() {

        viewDraft()

        saveDraft()

        binding.imageViewKTP.setOnClickListener {
            val intent = Intent(this, PendaftaranUploadKTPActivity::class.java)
            startActivityForResult(intent, CAMERA_CODE)
        }

        binding.imageButtonBack.setOnClickListener {
            this.onBackPressed()
        }

        binding.buttonSelanjutnya.setOnClickListener {
            if (sessionManager.ktp_document == "") {
                showToast(this, getString(R.string.document_ktp_empty))
            } else if (sessionManager.ktp_name == "") {
                binding.editTextNamaKTP.error = getString(R.string.nama_ktp_empty)
            } else if (sessionManager.ktp_id == "") {
                binding.editTextNOKTP.error = getString(R.string.no_ktp_empty)
            } else {
                with(sessionManager) {
                    val param = RequestRegisterDriver(
                        email = email,
                        phone = username.substring(2),
                        phone_number = username,
                        user_nationid = ktp_id,
                        checked = "true"
                    )

                    viewModel.registerDriver(param)
                }

            }
        }

    }

    private fun viewDraft() {
        binding.editTextNamaKTP.setText(if (sessionManager.ktp_name != "") sessionManager.ktp_name else "")
        binding.editTextNOKTP.setText(if (sessionManager.ktp_id != "") sessionManager.ktp_id else "")
    }

    private fun saveDraft() {
        binding.editTextNamaKTP.doAfterTextChanged { sessionManager.ktp_name = it.toString() }
        binding.editTextNOKTP.doAfterTextChanged { sessionManager.ktp_id = it.toString() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_CODE) {
            viewImage(data)
        }

    }

    private fun viewImage(data: Intent?) {
        image_path = data?.getStringExtra("photo_ktp_path").toString()
        sessionManager.ktp_document = image_path.toString()
        val bitmapOption = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(image_path, bitmapOption)
        binding.imageViewKTP.setImageBitmap(bitmap)
    }
}