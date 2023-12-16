package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranBinding
import com.surelabsid.mrjempootdriver.databinding.DialogGenderBinding
import com.surelabsid.mrjempootdriver.databinding.DialogRelationshipBinding
import com.surelabsid.mrjempootdriver.databinding.ItemRelationshipBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.adapter.ListRelationshipAdapter
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.DataItemRelationShip
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRelationship
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class PendaftaranActivity : AppCompatActivity() {

    lateinit var binding: ActivityPendaftaranBinding

    lateinit var viewModel: PendaftaranViewModel

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var dialogRelationship: DialogRelationshipBinding

    lateinit var bottomSheetRelationship: BottomSheetDialog

    private lateinit var dialogGender: DialogGenderBinding

    private lateinit var bottomSheetGender: BottomSheetDialog

    private var image_path: String? = null

    private var mime_type: String? = null

    private var relationship: ResponseRelationship? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initPermissionStorageAndCamera()
        initPermissionLocation()

        initView()

        attachObserve()

        binding.buttonSelanjutnya.setOnClickListener {
            if (TextUtils.isEmpty(binding.editTextNama.text.toString())) {
                binding.editTextNama.error = getString(R.string.name_empty)
            } else if (TextUtils.isEmpty(binding.editTextNoHp.text.toString())) {
                binding.editTextNoHp.error = getString(R.string.name_empty)
            } else if (TextUtils.isEmpty(binding.editTextEmail.text.toString())) {
                binding.editTextEmail.error = getString(R.string.name_empty)
            } else if (TextUtils.isEmpty(binding.editTextAlamat.text.toString())) {
                binding.editTextAlamat.error = getString(R.string.name_empty)
            } else if (TextUtils.isEmpty(binding.textTanggalLahir.text.toString())) {
                binding.textTanggalLahir.error = getString(R.string.name_empty)
            } else if (sessionManager.photo_profile == "") {
                showToast(this, getString(R.string.photo_profile_empty))
            } else if (TextUtils.isEmpty(binding.editTextNoKontakDarurat.text.toString())) {
                binding.editTextNoKontakDarurat.error = getString(R.string.name_empty)
            } else {
                val param = RequestRegisterDriver(
                    email = binding.editTextEmail.text.toString(),
                    phone_number = "${sessionManager.dial_code.substring(1)}${binding.editTextNoHp.text.toString()}",
                    checked = "true"
                )
                viewModel.registerDriver(param)
            }


        }

        binding.editTextAlamat.setOnClickListener {
            val intent = Intent(this@PendaftaranActivity, PendaftaranPetaActivity::class.java)
            startActivity(intent)
        }

        binding.imageButtonBack.setOnClickListener { super@PendaftaranActivity.onBackPressed() }
    }


    private fun initView() {

        viewDraft()

        saveDraft()

        viewModel.listRelationship()

        binding.buttonSelectPhoneCodeDarurat.setOnClickListener {
            dialogSelectCountry()
        }

        binding.textGender.setOnClickListener {
            showBotomSheetGender()
        }

        binding.editTextAlamat.setOnClickListener {
            openActivity(PendaftaranPetaActivity::class.java)
        }


        binding.layoutFotoProfil.setOnClickListener {
            openCamera()
        }
    }

    private fun dialogSelectCountry() {
        val picker = CountryPicker.newInstance("Select Country", Theme.DARK)
        picker.setListener { name, code, dialCode, flagDrawableResID ->

            sessionManager.emergency_flag_dial_code = flagDrawableResID
            sessionManager.emergency_dial_code = dialCode

            binding.buttonSelectPhoneCodeDarurat.setText(sessionManager.emergency_dial_code)
            binding.imageViewFlagDarurat.setImageResource(sessionManager.emergency_flag_dial_code)
            picker.dismiss()

        }

        picker.show(supportFragmentManager, "COUNTRY_PICKER")
    }


    private fun viewDraft() {

//        if (sessionManager.photo_profile != "") viewPhotoProfile(File(sessionManager.photo_profile))

        binding.imageViewFlag.setImageResource(if (sessionManager.flag_dial_code != 0) sessionManager.flag_dial_code else R.drawable.indo_flag)
        binding.buttonSelectPhoneCode.setText(if (sessionManager.dial_code != "") sessionManager.dial_code else "+62")
        binding.editTextNoHp.setText(if (sessionManager.username != "") sessionManager.username else "")
        binding.textTanggalLahir.setText(if (sessionManager.birth_date != "") sessionManager.birth_date else "")

        binding.editTextNama.setText(if (sessionManager.full_name != "") sessionManager.full_name else "")
        binding.editTextEmail.setText(if (sessionManager.email != "") sessionManager.email else "")
        binding.editTextAlamat.setText(if (sessionManager.address != "") "${sessionManager.address}, ${sessionManager.address_detail}" else "")
//        binding.editTextAlamat.setText(if (sessionManager.address != "") "${sessionManager.lat_address}, ${sessionManager.lng_address}" else "")
        binding.imageViewFlag.setImageResource(if (sessionManager.emergency_flag_dial_code != 0) sessionManager.emergency_flag_dial_code else R.drawable.indo_flag)
        binding.buttonSelectPhoneCodeDarurat.setText(if (sessionManager.emergency_dial_code != "") sessionManager.emergency_dial_code else "")
        binding.editTextNoKontakDarurat.setText(if (sessionManager.emergency_phone_number != "") sessionManager.emergency_phone_number else "")
        binding.editTextNamaKontakDarurat.setText(if (sessionManager.emergency_name != "") sessionManager.emergency_name else "")
        binding.textGender.text = if (sessionManager.gender != "") sessionManager.gender else ""
        binding.textKontakDarurat.text =
            if (sessionManager.emergency_relation != "") sessionManager.emergency_relation else ""

    }

    private fun viewPhotoProfile(uri_file: File) {
        var uri: Uri? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                uri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".provider",
                    uri_file
                )
            } else {
                uri = Uri.fromFile(uri_file)
            }

            binding.imageViewFotoProfil.setImageURI(uri)

        } catch (e: IllegalStateException) {
            AlertDialog.Builder(this).apply {
                setTitle("Exception")
                setMessage(e.message)
                setPositiveButton("OK") { _, _ -> }.show()
            }
        }
    }


    private fun saveDraft() {
        binding.editTextNama.doAfterTextChanged { sessionManager.full_name = it.toString() }
        binding.editTextEmail.doAfterTextChanged { sessionManager.email = it.toString() }
        binding.editTextNoKontakDarurat.doAfterTextChanged {
            sessionManager.emergency_phone_number = it.toString()
        }
        binding.editTextNamaKontakDarurat.doAfterTextChanged {
            sessionManager.emergency_name = it.toString()
        }

        binding.textTanggalLahir.setOnClickListener {
            val tanggalLahir =
                MaterialDatePicker.Builder.datePicker().setTitleText("Pilih tanggal lahir").build()
            supportFragmentManager.let { it1 -> tanggalLahir.show(it1, "MATERIAL_DATE_PICKER") }

            tanggalLahir.addOnPositiveButtonClickListener {
                binding.textTanggalLahir.text = formatDatePickertoShow(Date(it).toString())
                sessionManager.birth_date = formatDatePicker(Date(it).toString())
            }
        }

        binding.textGender.doAfterTextChanged { sessionManager.gender = it.toString() }

        binding.textKontakDarurat.setOnClickListener {
            showBottomSheetRelationship()
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


    private fun showBottomSheetRelationship() {
        dialogRelationship =
            DialogRelationshipBinding.bind(View.inflate(this, R.layout.dialog_relationship, null))
        bottomSheetRelationship = BottomSheetDialog(this).apply {
            setContentView(dialogRelationship.root)
            show()
        }

        with(dialogRelationship) {
            val adapter = ListRelationshipAdapter(
                this@PendaftaranActivity,
                relationship?.data,
                object : ListRelationshipAdapter.OnClickListener {
                    override fun itemClick(item: DataItemRelationShip?) {
                        sessionManager.emergency_relation_id = item?.id.toString()
                        sessionManager.emergency_relation = item?.relationship.toString()

                        binding.textKontakDarurat.text = item?.relationship.toString()

                        bottomSheetRelationship.dismiss()
                    }
                })
            listRelationship.adapter = adapter
        }
    }

    private fun attachObserve() {
        with(viewModel) {
            register_driver.observe(this@PendaftaranActivity, { responseRegisterDriver(it) })
            list_relationship.observe(this@PendaftaranActivity, { responseRelationship(it) })
            loading.observe(this@PendaftaranActivity, { isLoading(it) })
            error.observe(this@PendaftaranActivity, { showError(this@PendaftaranActivity, it) })
        }
    }

    private fun responseRelationship(it: ResponseRelationship?) {
        relationship = it
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


    private fun responseRegisterDriver(it: ResponseRegisterDriver?) {
        if (it?.code == "200") {
            openActivity(PendaftaranFotoKTPActivity::class.java)
        } else if (it?.code == "201") {
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_LONG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
        }
    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "PurchaseImage$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageViewFotoProfil.setImageBitmap(BitmapFactory.decodeFile(image_path))

            //save draft
            sessionManager.photo_profile = image_path ?: ""

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    override fun onResume() {
        super.onResume()
        viewDraft()
    }


}