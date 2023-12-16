package com.surelabsid.mrjempootdriver.ui.profil

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityDataPribadiBinding
import com.surelabsid.mrjempootdriver.databinding.DialogGenderBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestEditProfile
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.pendaftaran.PendaftaranPetaActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseEditProfile
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.random.Random

@AndroidEntryPoint
class DataPribadiActivity : BaseActivity() {

    private lateinit var binding: ActivityDataPribadiBinding

    lateinit var viewModel: ProfileViewModel

    private var image_path: String? = null

    private var mime_type: String? = null

    private lateinit var bottomSheetGender: BottomSheetDialog

    private lateinit var dialogGender: DialogGenderBinding

    private var birth_date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataPribadiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        initPermissionCamera()

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            edit_profile.observe(this@DataPribadiActivity, { responseEditProfile(it) })
            error.observe(this@DataPribadiActivity, { showError(this@DataPribadiActivity, it) })
            loading.observe(this@DataPribadiActivity, { isLoading(it) })
        }
    }

    private fun responseEditProfile(it: ResponseEditProfile?) {
        if (it?.code == "200") {
            val item = it.data?.get(0)
            with(sessionManager) {
                full_name = item?.driverName.toString()
                birth_date = item?.dob.toString()
                photo_profile = item?.photo.toString()
                gender = item?.gender.toString()
                address = item?.driverAddress.toString()
            }

            showToast(this, it.message)

            finish()
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                buttonSimpan.visibility = View.INVISIBLE
            }
        } else {
            with(binding) {
                buttonSimpan.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun initView() {
        with(binding) {

            birth_date = sessionManager.birth_date

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            editTextNama.setText(sessionManager.full_name)
            textGender.setText(sessionManager.gender)
            textTanggalLahir.setText(sessionManager.birth_date)
            textAlamat.setText(sessionManager.address)

            sessionManager.address

            binding.textAlamat.setOnClickListener {
                sessionManager.flag_update = true
                val intent = Intent(this@DataPribadiActivity, PendaftaranPetaActivity::class.java)
                startActivity(intent)
            }

            binding.textGender.setOnClickListener {
                showBotomSheetGender()
            }

            binding.layoutProfile.setOnClickListener {
                openCamera()
            }

            binding.textTanggalLahir.setOnClickListener {
                val tanggalLahir =
                    MaterialDatePicker.Builder.datePicker().setTitleText("Pilih tanggal lahir").build()
                supportFragmentManager.let { it1 -> tanggalLahir.show(it1, "MATERIAL_DATE_PICKER") }

                tanggalLahir.addOnPositiveButtonClickListener {
                    binding.textTanggalLahir.text = formatDatePickertoShow(Date(it).toString())
                    birth_date = formatDatePicker(Date(it).toString())
                }
            }

            binding.buttonSimpan.setOnClickListener {
                val param = RequestEditProfile(
                    customer_fullname = editTextNama.text.toString(),
                    dob = birth_date ?: "",
                    fotodriver_lama = if (image_path != null) sessionManager.photo_profile else "",
                    fotodriver = if (image_path != null) encodeBase64(image_path) else "",
                    phone_number = sessionManager.username,
                    gender = textGender.text.toString(),
                    driver_address = textAlamat.text.toString()
                )

                viewModel.editProfile(param)
            }

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

            name_file = "PhotoProfile$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageViewFotoProfil.setImageBitmap(BitmapFactory.decodeFile(image_path))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private fun showBotomSheetGender() {
        dialogGender = DialogGenderBinding.bind(View.inflate(this, R.layout.dialog_gender, null))
        bottomSheetGender = BottomSheetDialog(this).apply {
            setContentView(dialogGender.root)
            show()
        }

        with(dialogGender) {
            textMale.setOnClickListener {
                binding.textGender.text = textMale.text.toString()
                bottomSheetGender.dismiss()
            }
            textFemale.setOnClickListener {
                binding.textGender.text = textFemale.text.toString()
                bottomSheetGender.dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (sessionManager.flag_update) {
            binding.textAlamat.text = sessionManager.address_temp
        }
    }


}