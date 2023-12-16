package com.surelabsid.mrjempootdriver.ui.lupapassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityLupaPasswordBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginVerifikasiActivity
import com.surelabsid.mrjempootdriver.utils.moveActivity
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LupaPasswordActivity : BaseActivity() {

    lateinit var binding: ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        with(binding) {

            imageButtonBack.setOnClickListener {
                sessionManager.flag_reset_password = false
                onBackPressed()
            }

            //Select Country
            buttonSelectPhoneCode.setOnClickListener {
                dialogSelectCountry()
            }

            editTextNoTelepon.doAfterTextChanged { sessionManager.username = it.toString() }

            buttonSelanjutnya.setOnClickListener {
                sessionManager.flag_reset_password = true
                openActivity(LoginVerifikasiActivity::class.java)
            }
        }

    }

    private fun dialogSelectCountry() {
        val picker = CountryPicker.newInstance("Select Country", Theme.DARK)
        picker.setListener { name, code, dialCode, flagDrawableResID ->

            sessionManager.dial_code = dialCode
            binding.buttonSelectPhoneCode.setText(dialCode)
            binding.imageViewFlag.setImageResource(flagDrawableResID)
            picker.dismiss()

        }

        picker.show(supportFragmentManager, "COUNTRY_PICKER")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sessionManager.flag_reset_password = false
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.flag_reset_password = false
    }

}