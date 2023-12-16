package com.surelabsid.mrjempootdriver.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityLoginBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.LoginViewModel
import com.surelabsid.mrjempootdriver.ui.lupapassword.LupaPasswordActivity
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            login.observe(this@LoginActivity, { responseLogin(it) })
            loading.observe(this@LoginActivity, { isLoading(it) })
            error.observe(this@LoginActivity, { showError(this@LoginActivity, it)})
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                buttonMasuk.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                buttonMasuk.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun responseLogin(responseLogin: ResponseLogin) {

        if (responseLogin.code == "200") {
            val item = responseLogin.data?.get(0)

            with(sessionManager) {
                id = item?.id.toString()
                full_name = item?.driverName.toString()
                ktp_id = item?.userNationid.toString()
                birth_date = item?.dob.toString()
                username = item?.phoneNumber.toString()
                dial_code = item?.countrycode.toString()
                email = item?.email.toString()
                photo_profile = item?.photo.toString()
                rating = item?.rating.toString()
                job = item?.job.toString()
                gender = item?.gender.toString()
                address = item?.driverAddress.toString()
                status = item?.status.toString()
                status_config_driver = item?.statusConfigDriver.toString()
                balance = item?.balance.toString()
                vehicle_plate = item?.vehicleRegistrationNumber.toString()
                vehicle_color = item?.color.toString()
                vehicle_brand = item?.brand.toString()

                createLoginSession(item?.regId.toString())

            }

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        } else {
            binding.root.showSnackbar("${responseLogin.message}", Snackbar.LENGTH_LONG)
        }

    }

    private fun initView() {

        saveDraft()

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.textView83.setOnClickListener {
            openActivity(LupaPasswordActivity::class.java)
        }

        binding.buttonMasuk.setOnClickListener {

            if (TextUtils.isEmpty(binding.editTextNoTelepon.text.toString())) {
                binding.editTextNoTelepon.error = getString(R.string.phone_empty)
            } else if (TextUtils.isEmpty(binding.editTextPassword.text.toString())) {
                binding.editTextPassword.error = getString(R.string.password_empty)
            } else {
                val phone_number = sessionManager.dial_code.substring(1) + sessionManager.username
                val password = sessionManager.password
                val token = sessionManager.token
                val param = RequestLogin(phone_number, password, token)
                viewModel.login(param)
            }
        }

        //Select Country
        binding.buttonSelectPhoneCode.setOnClickListener {
            dialogSelectCountry()
        }
    }

    private fun saveDraft() {
        binding.editTextNoTelepon.doAfterTextChanged {
            sessionManager.username = it.toString()
        }
        binding.editTextPassword.doAfterTextChanged {
            sessionManager.password = it.toString()
        }
    }

    private fun dialogSelectCountry() {
        val picker = CountryPicker.newInstance("Select Country", Theme.DARK)
        picker.setListener { name, code, dialCode, flagDrawableResID ->

            sessionManager.flag_dial_code = flagDrawableResID
            sessionManager.dial_code = dialCode

            binding.buttonSelectPhoneCode.setText(sessionManager.dial_code)
            binding.imageViewFlag.setImageResource(sessionManager.flag_dial_code)
            picker.dismiss()

        }

        picker.show(supportFragmentManager, "COUNTRY_PICKER")
    }

}