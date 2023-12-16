package com.surelabsid.mrjempootdriver.ui.pendaftaranselesai

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityBuatPasswordBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.moveActivity
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuatPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityBuatPasswordBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuatPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {

        with(viewModel) {
            reset_password.observe(this@BuatPasswordActivity, { responseResetPassword(it) })
            error.observe(this@BuatPasswordActivity, { showError(this@BuatPasswordActivity, it) })
            loading.observe(this@BuatPasswordActivity, { isLoading(it) })
        }


    }

    private fun responseResetPassword(it: ResponseReqTopup?) {
        if (it?.code == "200") {

            sessionManager.flag_create_password = false

            moveActivity(BuatPasswordSelesaiActivity::class.java)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }

    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                buttonResetPassword.visibility = View.GONE
            }
        } else {
            with(binding) {
                buttonResetPassword.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

    }



    private fun initView() {

        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
                sessionManager.flag_create_password = false
            }

            buttonResetPassword.setOnClickListener {
                if (TextUtils.isEmpty(editTextPassword.text.toString())) {
                    editTextPassword.error = getString(R.string.password_empty)
                }
                else if (TextUtils.isEmpty(editTextPassword2.text.toString())) {
                    editTextPassword2.error = getString(R.string.password_empty)
                }
                else if (editTextPassword.text.toString() != editTextPassword2.text.toString()) {
                    showToast(this@BuatPasswordActivity, getString(R.string.password_not_macth))
                } else {
                    var phone_number = ""
                    if (sessionManager.flag_create_password) {
                        phone_number = sessionManager.dial_code.substring(1)+sessionManager.username
                    }
                    else if (sessionManager.flag_reset_password) {
                        phone_number = sessionManager.dial_code.substring(1)+sessionManager.username
                    } else {
                        phone_number = sessionManager.username
                    }
                    val param = RequestResetPassword(
                        phone_number = phone_number,
                        password = editTextPassword.text.toString()
                    )

                    viewModel.resetPassword(param)
                }
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        sessionManager.flag_create_password = false
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.flag_create_password = false
    }
}