package com.surelabsid.mrjempootdriver.ui.profil

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityEditProfileBinding
import com.surelabsid.mrjempootdriver.databinding.DialogOtpPhoneNumberBinding
import com.surelabsid.mrjempootdriver.databinding.DialogVerifikasiEmailBinding
import com.surelabsid.mrjempootdriver.databinding.DialogVerifikasiPhoneBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseRequestOtp
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.LoginViewModel
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.lupapassword.ResetPasswordActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.JenisTransportActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.*
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_DRIVER_PHOTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {

    lateinit var binding: ActivityEditProfileBinding

    private lateinit var viewModel: ProfileViewModel

    private lateinit var viewModelLogin: LoginViewModel

    private lateinit var bottomSheet: BottomSheetDialog

    private lateinit var dialogVerifikasiEmail: DialogVerifikasiEmailBinding

    private lateinit var dialogVerifikasiPhone: DialogVerifikasiPhoneBinding

    private lateinit var dialogOtpPhoneNumber: DialogOtpPhoneNumberBinding

    private var phone_number_temp: String? = null

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var time_in_milli_seconds = 30000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)

        initView()

        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            edit_profile.observe(this@EditProfileActivity, { responseEditProfile(it) })
            send_email_verify.observe(this@EditProfileActivity, { responseSendEmailVerify(it) })
            change_email_driver.observe(this@EditProfileActivity, { responseChangeEmailDriver(it) })
            check_email_driver.observe(this@EditProfileActivity, { responseCheckEmailDriver(it) })
            change_phone_driver.observe(this@EditProfileActivity, { responseChangePhoneDriver(it) })
            error.observe(this@EditProfileActivity, { showError(this@EditProfileActivity, it) })
            loading.observe(this@EditProfileActivity, { isLoading(it) })
        }

        with(viewModelLogin) {
            request_otp.observe(this@EditProfileActivity, { responseRequestOtp(it) })
            verify_otp.observe(this@EditProfileActivity, { responseVerifyOtp(it) })
        }
    }

    private fun responseChangePhoneDriver(it: ResponseChangePhoneDriver?) {
        if (it?.code == "200") {
            val item = it.data
            with(sessionManager) {
                username = item?.phoneNumber.toString()
            }

            initView()

            showToast(this, it.message)

            bottomSheet.dismiss()

        } else {
            showToast(this, "${it?.code}")
        }
    }

    private fun responseCheckEmailDriver(it: ResponseCheckEmailDriver?) {
        if (it?.code == "200") {

            val item = it.data
            sessionManager.email = item?.email.toString()
            with(binding) {
                if (item?.isVerify == "no") {
                    textView40.text = "Belum terverifikasi"
                    textView40.setCompoundDrawables(null, null, null, null)
                } else {
                    textView40.text = "Sudah terverifikasi"
                }
            }
        }
    }

    private fun responseRequestOtp(it: ResponseRequestOtp?) {
        if (it?.code == 201) {
            showBottomSheetOtpPhoneNumber()
            binding.root.showSnackbar("${it.message}", Snackbar.LENGTH_SHORT)
//            binding.root.showSnackbar("${it.message} ${it.otp}", Snackbar.LENGTH_SHORT)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseVerifyOtp(it: ResponseRequestOtp?) {
        if (it?.code == 200) {

//            val param = RequestEditProfile(
//                customer_fullname = sessionManager.full_name,
//                dob = sessionManager.birth_date,
//                phone_number = phone_number_temp ?: sessionManager.username,
//            )

            val param = RequestChangePhoneDriver(
                sessionManager.id,
                "+$phone_number_temp"
            )

            viewModel.changePhoneDriver(param)
//            viewModel.editProfile(param)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }



    private fun responseSendEmailVerify(it: ResponseSendEmailVerify?) {
        if (it?.code == "200") {
//            binding.root.showSnackbar(it.data?.message.toString(), Snackbar.LENGTH_SHORT)
            bottomSheet.dismiss()

            val param = RequestChangeEmailDriver(sessionManager.username, it.data?.email.toString())
            viewModel.changeEmailDriver(param)
        } else {
            showToast(this, "${it?.code}")
        }
    }

    private fun responseChangeEmailDriver(it: ResponseChangeEmailDriver?) {
        if (it?.code == "200") {
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_SHORT)
            bottomSheet.dismiss()

            sessionManager.email = it.data?.email.toString()

            binding.textEmail.text = sessionManager.email
            binding.textView40.text = "Belum terverifikasi"
            binding.textView40.setCompoundDrawables(null, null, null, null)


            val param = RequestCheckEmailDriver(sessionManager.username)
            viewModel.checkEmailDriver(param)

        } else {
            showToast(this, "${it?.code}")
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(dialogOtpPhoneNumber) {
                progressBar.visibility = View.VISIBLE
                buttonVerifikasi.visibility = View.GONE
            }

            with(dialogVerifikasiEmail) {
                progressBar.visibility = View.VISIBLE
                buttonVerifikasi.visibility = View.GONE
            }

        } else {
            with(dialogOtpPhoneNumber) {
                buttonVerifikasi.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            with(dialogVerifikasiEmail) {
                buttonVerifikasi.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

        }
    }

    private fun responseEditProfile(it: ResponseEditProfile?) {
        if (it?.code == "200") {
            val item = it.data?.get(0)
            with(sessionManager) {
                username = item?.phoneNumber.toString()
            }

            initView()

            showToast(this, it.message)

            bottomSheet.dismiss()

        } else {
            showToast(this, "${it?.code}")
        }
    }



    private fun initView() {
        with(binding) {
            textViewNama.text = sessionManager.full_name
            textViewJK.text = sessionManager.gender
            textViewTanggalLahir.text = sessionManager.birth_date
            textViewAlamat.text = sessionManager.address

            Glide.with(this@EditProfileActivity)
                .load(BASE_URL_DRIVER_PHOTO + sessionManager.photo_profile)
                .transform(
                    CenterCrop()
                )
                .error(R.drawable.bg_beranda).into(imageViewFotoProfil)

            textEmail.text = sessionManager.email
            textPhoneNumber.text = "+${sessionManager.username}"
            textViewPlat.text = sessionManager.vehicle_plate
            textViewWarna.text = sessionManager.vehicle_color
            textViewMerk.text = sessionManager.vehicle_brand

            val param = RequestCheckEmailDriver(sessionManager.username)
            viewModel.checkEmailDriver(param)

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            textViewUbah.setOnClickListener {
                openActivity(DataPribadiActivity::class.java)
            }

            button3.setOnClickListener {
                showBottomSheetVerifikasiEmail()
            }

            button4.setOnClickListener {
                showBottomSheetVerifikasiPhone()
            }

            button5.setOnClickListener {
                openActivity(ResetPasswordActivity::class.java)
            }


        }

        dialogVerifikasiEmail = DialogVerifikasiEmailBinding.bind(
            View.inflate(
                this,
                R.layout.dialog_verifikasi_email,
                null
            )
        )

        dialogVerifikasiPhone = DialogVerifikasiPhoneBinding.bind(
            View.inflate(
                this,
                R.layout.dialog_verifikasi_phone,
                null
            )
        )

        dialogOtpPhoneNumber = DialogOtpPhoneNumberBinding.bind(
            View.inflate(
                this,
                R.layout.dialog_otp_phone_number,
                null
            )
        )
    }

    private fun showBottomSheetVerifikasiEmail() {
        bottomSheet = BottomSheetDialog(this).apply {
            setContentView(dialogVerifikasiEmail.root)
            show()
        }

        with(dialogVerifikasiEmail) {

//            textEmail.setText(sessionManager.email)

            buttonVerifikasi.setOnClickListener {
                if (TextUtils.isEmpty(dialogVerifikasiEmail.textEmail.text.toString())) {
                    dialogVerifikasiEmail.textEmail.error = getString(R.string.email_empty)
                } else {
                    val param = RequestSendEmailVerify(sessionManager.id, dialogVerifikasiEmail.textEmail.text.toString())
                    viewModel.sendEmailVerify(param)
                }
            }
        }
    }

    private fun showBottomSheetVerifikasiPhone() {
        bottomSheet = BottomSheetDialog(this).apply {
            setContentView(dialogVerifikasiPhone.root)
            show()
        }

        with(dialogVerifikasiPhone) {
//            textPhone.setText("${sessionManager.username}")

//            textPhone.doAfterTextChanged { phone_number_temp = it.toString() }
            buttonVerifikasi.setOnClickListener {
                phone_number_temp = textPhone.text.toString()
                bottomSheet.dismiss()

                if (phone_number_temp != null) {
                    if (!sessionManager.is_dev) {
                        viewModelLogin.requestOtp("${phone_number_temp}")
                    } else {
                        showBottomSheetOtpPhoneNumber()
                    }
                } else {
                    textPhone.error = getString(R.string.phone_empty)
                }

            }
        }
    }

    private fun showBottomSheetOtpPhoneNumber() {
//        dialogOtpPhoneNumber = DialogOtpPhoneNumberBinding.bind(
//            View.inflate(
//                this,
//                R.layout.dialog_otp_phone_number,
//                null
//            )
//        )
        bottomSheet = BottomSheetDialog(this).apply {
            setContentView(dialogOtpPhoneNumber.root)
            show()
        }

        with(dialogOtpPhoneNumber) {
            textViewNomorHandphone.text = "+${phone_number_temp}"

            startTimer(time_in_milli_seconds)

            numone.doOnTextChanged { text, start, before, count ->
                when(count){
                    1 -> {
                        numtwo.requestFocus()
                        numtwo.selectAll()
                    }
                }
            }

            numtwo.doOnTextChanged { text, start, before, count ->
                when(count){
                    0 -> {
                        numone.requestFocus()
                        numone.selectAll()
                    }
                    1 -> {
                        numthree.requestFocus()
                        numthree.selectAll()

                    }
                }
            }

            numthree.doOnTextChanged { text, start, before, count ->
                when(count){
                    0 -> {
                        numtwo.requestFocus()
                        numtwo.selectAll()
                    }
                    1 -> {
                        numfour.requestFocus()
                        numfour.selectAll()
                    }
                }
            }

            numfour.doOnTextChanged { text, start, before, count ->
                when(count){
                    0 -> {
                        numthree.requestFocus()
                        numthree.selectAll()
                    }
                    1 -> {
//                    binding.numfive.requestFocus()
//                    binding.numfive.selectAll()
                    }
                }
            }

            buttonVerifikasi.setOnClickListener {

                if (!sessionManager.is_dev) {
                    val kode_otp = "${numone.text}${numtwo.text}${numthree.text}${numfour.text}"
                    viewModelLogin.verifyOtp("${phone_number_temp}", kode_otp)
                } else {
//                    val param = RequestEditProfile(
//                        customer_fullname = sessionManager.full_name,
//                        dob = sessionManager.birth_date,
//                        phone_number = phone_number_temp ?: sessionManager.username,
//                        driver_address = sessionManager.address,
//                        gender = sessionManager.gender,
//                    )
//
//                    viewModel.editProfile(param)

                    val param = RequestChangePhoneDriver(
                        sessionManager.id,
                        "+$phone_number_temp"
                    )

                    viewModel.changePhoneDriver(param)
                }


            }

            buttonGantiNoTelepon.setOnClickListener {
                bottomSheet.dismiss()
                showBottomSheetVerifikasiPhone()
            }
        }
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
//                loadConfeti()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()

        isRunning = true

    }

    private fun updateTextUI() {
        var minute = (time_in_milli_seconds / 1000) / 60
        var seconds = (time_in_milli_seconds / 1000) % 60

        minute = if (minute < 10) "0$minute".toLong() else minute
        seconds = if (seconds < 10) "0$seconds".toLong() else seconds

        dialogOtpPhoneNumber.textViewTime.text = "Tunggu $minute:$seconds"
    }



    override fun onResume() {
        super.onResume()
        initView()
    }

}