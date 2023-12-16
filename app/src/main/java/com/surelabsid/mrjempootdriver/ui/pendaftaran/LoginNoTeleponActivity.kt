package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityLoginNoTeleponBinding
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme
import dagger.hilt.android.AndroidEntryPoint
import android.R.attr.button
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseStatusPendaftaran
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.ui.pendaftaranselesai.PendaftaranSelesaiActivity
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.utils.moveActivity
import com.surelabsid.mrjempootdriver.utils.openActivity
import javax.inject.Inject


@AndroidEntryPoint
class LoginNoTeleponActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginNoTeleponBinding

    lateinit var viewModel: PendaftaranViewModel

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginNoTeleponBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()
        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            status_pendaftaran.observe(
                this@LoginNoTeleponActivity,
                { responseStatusPendaftaran(it) })
            error.observe(
                this@LoginNoTeleponActivity,
                { moveActivity(LoginVerifikasiActivity::class.java) })
        }
    }

    private fun responseStatusPendaftaran(it: ResponseStatusPendaftaran?) {
        if (it?.code == "200") {
//            val statusValue = it.data?.status ?: "null"
//            Log.d("statusValue register:", statusValue)
            when (it.data?.status) {
                "0" -> {
                    val intent = Intent(this, LoginVerifikasiActivity::class.java).apply {
                        putExtra("status_pendaftaran", it.data.status)
                    }
                    startActivity(intent)
                }
                "1" -> {
                    val intent = Intent(this, LoginVerifikasiActivity::class.java).apply {
                        putExtra("status_pendaftaran", it.data.status)
                    }
                    startActivity(intent)
                }
                "2" -> {
                    Intent(this, PendaftaranDitolakActivity::class.java).apply {
                        putExtra("reason_rejected", it.data.whyRejected)
                        startActivity(this)
                        finish()
                    }
                }
                else -> {
                    moveActivity(LoginVerifikasiActivity::class.java)
                }
            }
        } else {
            moveActivity(LoginVerifikasiActivity::class.java)
        }
    }


    private fun initView() {

        saveDraft()

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }

        //Select Country
        binding.buttonSelectPhoneCode.setOnClickListener {
            dialogSelectCountry()
        }

        binding.buttonSelanjutnya.setOnClickListener {

            if (TextUtils.isEmpty(binding.editTextNoTelepon.text.toString())) {
                binding.editTextNoTelepon.error = getString(R.string.phone_empty)
            } else {
                Log.d("statusValue register:", "0000")
                viewModel.statusPendaftaran(sessionManager.dial_code.substring(1) + sessionManager.username)
            }
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

    private fun saveDraft() {
        binding.editTextNoTelepon.doAfterTextChanged {
            sessionManager.username = it.toString()
        }
    }
}