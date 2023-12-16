package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityInformasiBankBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.HistoryWalletAdapter
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRekBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseListBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BANK_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.CAMERA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class InformasiBankActivity : AppCompatActivity() {

    lateinit var binding: ActivityInformasiBankBinding

    private lateinit var viewModel: PendaftaranViewModel

    @Inject
    lateinit var sessionManager: SessionManager

    private var bank_id: String? = null

    private var image_path: String? = null

    private var mime_type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            rek_bank.observe(this@InformasiBankActivity, { responseRekBank(it) })
            error.observe(this@InformasiBankActivity, { showError(this@InformasiBankActivity, it) })
            loading.observe(this@InformasiBankActivity, { isLoading(it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                progressBar.visibility = android.view.View.VISIBLE
                buttonSimpan.visibility = android.view.View.INVISIBLE
            }
        } else {
            with(binding) {
                buttonSimpan.visibility = android.view.View.VISIBLE
                progressBar.visibility = android.view.View.INVISIBLE
            }
        }

    }

    private fun responseRekBank(it: ResponseListBank?) {
        if (it?.code == "200") {
             finish()
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }

    private fun initView() {

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.uploadRekening.setOnClickListener {
            openCamera()
        }

        binding.pilihNamaBank.setOnClickListener {
            val intent = Intent(this, PilihBankActivity::class.java)
            startActivityForResult(intent, BANK_RESULT)
        }

        binding.buttonSimpan.setOnClickListener {

            if (image_path == "") {
                showToast(this, getString(R.string.photo_bank_empty))
            } else if (TextUtils.isEmpty(binding.pilihNamaBank.text.toString())) {
                binding.pilihNamaBank.error = getString(R.string.bank_name_empty)
            } else if (TextUtils.isEmpty(binding.editTextNoRekening.text.toString())) {
                binding.editTextNoRekening.error = getString(R.string.bank_rekening_empty)
            } else if (TextUtils.isEmpty(binding.editTextNamaPenerima.text.toString())) {
                binding.editTextNamaPenerima.error = getString(R.string.bank_account_name_empty)
            } else {
                val param = RequestRekBank(
                    user_id = sessionManager.id,
                    id_bank = bank_id ?: "",
                    no_rek = binding.editTextNoRekening.text.toString(),
                    nama_rek = binding.editTextNamaPenerima.text.toString(),
                    buku_tab = image_path ?: ""
                )
                viewModel.rekBank(param)

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
        } else if (requestCode == BANK_RESULT) {
            binding.pilihNamaBank.text = data?.getStringExtra("nama_bank").toString()
            bank_id = data?.getStringExtra("id_bank").toString()
        }

    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "UploadRekening$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageViewFotoRekening.setImageBitmap(BitmapFactory.decodeFile(image_path))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }
}