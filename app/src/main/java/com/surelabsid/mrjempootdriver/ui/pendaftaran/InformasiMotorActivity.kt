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
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityInformasiMotorBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_SIM_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_SKCK_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_STNK_CODE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class InformasiMotorActivity : AppCompatActivity() {

    lateinit var binding: ActivityInformasiMotorBinding

    lateinit var viewModel: PendaftaranViewModel

    @Inject
    lateinit var sessionManager: SessionManager

    private var image_path_sim: String? = null
    private var image_path_stnk: String? = null
    private var image_path_skck: String? = null

    private var mime_type_sim: String? = null
    private var mime_type_stnk: String? = null
    private var mime_type_skck: String? = null

    private var is_motor_driver: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            register_driver.observe(this@InformasiMotorActivity, { responseRegister(it) })
            loading.observe(this@InformasiMotorActivity, { isLoading(it) })
            error.observe(
                this@InformasiMotorActivity,
                { showError(this@InformasiMotorActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                buttonSelanjutnya.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                buttonSelanjutnya.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }


    private fun responseRegister(it: ResponseRegisterDriver?) {
        if (it?.code == "200") {
            openActivity(SyaratKetentuanActivity::class.java)
        } else if (it?.code == "201") {
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_LONG)
        }
    }


    private fun initView() {

        with(binding) {

            if (sessionManager.vehicle_kind == "Motor Driver") {

                appCompatTextView2.text = getString(R.string.informasi_kendaraan)

                textDesc.text = getString(R.string.kendaraan_sepeda_motor)
                labelNoPolisi.visibility = View.VISIBLE
                editTextNoPolisi.visibility = View.VISIBLE
                labelWarnaKendaraan.visibility = View.VISIBLE
                editTextWarnaKendaraan.visibility = View.VISIBLE
                labelMerkKendaraan.visibility = View.VISIBLE
                editTextMerkKendaraan.visibility = View.VISIBLE
                labelTipeKendaraan.visibility = View.VISIBLE
                editTextTipeKendaraan.visibility = View.VISIBLE
                uploadSTNK.visibility = View.VISIBLE
                viewBelowSTNK.visibility = View.VISIBLE
            } else {
                is_motor_driver = false

                appCompatTextView2.text = getString(R.string.informasi_driver)

                textDesc.text = getString(R.string.kendaraan_new_selis)
                labelNoPolisi.visibility = View.GONE
                editTextNoPolisi.visibility = View.GONE
                labelWarnaKendaraan.visibility = View.GONE
                editTextWarnaKendaraan.visibility = View.GONE
                labelMerkKendaraan.visibility = View.GONE
                editTextMerkKendaraan.visibility = View.GONE
                labelTipeKendaraan.visibility = View.GONE
                editTextTipeKendaraan.visibility = View.GONE
                uploadSTNK.visibility = View.GONE
                viewBelowSTNK.visibility = View.GONE
            }

            viewDraft()
            saveDraft()

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            uploadSIM.setOnClickListener {
                openCamera(CAMERA_SIM_CODE)
            }
            uploadSTNK.setOnClickListener {
                openCamera(CAMERA_STNK_CODE)
            }
            uploadSKCK.setOnClickListener {
                openCamera(CAMERA_SKCK_CODE)
            }

            buttonSelanjutnya.setOnClickListener {
                if (sessionManager.vehicle_plate == "" && is_motor_driver) {
                    binding.editTextNoPolisi.error = getString(R.string.plat_nomor_empty)
                } else if (sessionManager.vehicle_color == "" && is_motor_driver) {
                    binding.editTextWarnaKendaraan.error = getString(R.string.warna_kendaraan_empty)
                } else if (sessionManager.vehicle_brand == "" && is_motor_driver) {
                    binding.editTextMerkKendaraan.error = getString(R.string.merk_kendaraan_empty)
                } else if (sessionManager.sim_document_id == "") {
                    showToast(this@InformasiMotorActivity, getString(R.string.no_sim_empty))
                } else if (sessionManager.sim_document == "") {
                    showToast(this@InformasiMotorActivity, getString(R.string.document_sim_empty))
                } else if (sessionManager.stnk_document == "" && is_motor_driver) {
                    showToast(this@InformasiMotorActivity, getString(R.string.document_stnk_empty))
                } else {
                    with(sessionManager) {
                        val param = RequestRegisterDriver(
                            email = email,
                            phone = username.substring(2),
                            phone_number = username,
                            driver_license_id = sim_document_id,
                            user_nationid = ktp_id,
                            checked = "true"
                        )

                        viewModel.registerDriver(param)
                    }
                }
            }
        }
    }

    private fun viewDraft() {

        with(binding) {
            editTextNoPolisi.setText(if (sessionManager.vehicle_plate != "") sessionManager.vehicle_plate else "")
            editTextWarnaKendaraan.setText(if (sessionManager.vehicle_color != "") sessionManager.vehicle_color else "")
            editTextMerkKendaraan.setText(if (sessionManager.vehicle_brand != "") sessionManager.vehicle_brand else "")
            editTextTipeKendaraan.setText(if (sessionManager.vehicle_type != "") sessionManager.vehicle_type else "")
            editTextNoSIM.setText(if (sessionManager.sim_document_id != "") sessionManager.sim_document_id else "")
        }
    }

    private fun saveDraft() {

        with(binding) {
            editTextNoPolisi.doAfterTextChanged { sessionManager.vehicle_plate = it.toString() }
            editTextWarnaKendaraan.doAfterTextChanged {
                sessionManager.vehicle_color = it.toString()
            }
            editTextMerkKendaraan.doAfterTextChanged {
                sessionManager.vehicle_brand = it.toString()
            }
            editTextTipeKendaraan.doAfterTextChanged { sessionManager.vehicle_type = it.toString() }
            editTextNoSIM.doAfterTextChanged { sessionManager.sim_document_id = it.toString() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_SIM_CODE && resultCode == Activity.RESULT_OK) {
            initCameraSIM(data)
        } else if (requestCode == CAMERA_STNK_CODE && resultCode == Activity.RESULT_OK) {
            initCameraSTNK(data)
        } else if (requestCode == CAMERA_SKCK_CODE && resultCode == Activity.RESULT_OK) {
            initCameraSKCK(data)
        }

    }

    private fun initCameraSIM(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "SIM$random"

            image_path_sim = persistImage(image as Bitmap, name_file)

            mime_type_sim = getMimeTypeFile(Uri.parse(image_path_sim))

            Log.d("TAG", "initCamera: MimeType : $mime_type_sim")

            binding.imageViewSIM.setImageBitmap(BitmapFactory.decodeFile(image_path_sim))

            //save draft
            sessionManager.sim_document = image_path_sim ?: ""

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private fun initCameraSTNK(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "STNK$random"

            image_path_stnk = persistImage(image as Bitmap, name_file)

            mime_type_stnk = getMimeTypeFile(Uri.parse(image_path_stnk))

            Log.d("TAG", "initCamera: MimeType : $mime_type_stnk")

            binding.imageViewSTNK.setImageBitmap(BitmapFactory.decodeFile(image_path_stnk))

            //save draft
            sessionManager.stnk_document = image_path_stnk ?: ""

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private fun initCameraSKCK(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "SKCK$random"

            image_path_skck = persistImage(image as Bitmap, name_file)

            mime_type_skck = getMimeTypeFile(Uri.parse(image_path_skck))

            Log.d("TAG", "initCamera: MimeType : $mime_type_skck")

            binding.imageViewSKCK.setImageBitmap(BitmapFactory.decodeFile(image_path_skck))

            //save draft
            sessionManager.skck_document = image_path_skck ?: ""

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

}