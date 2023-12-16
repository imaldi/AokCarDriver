package com.surelabsid.mrjempootdriver.ui.lupapassword

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityResetPasswordBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestChangePassword
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginDaftarActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseEditProfile
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseLogout
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : BaseActivity() {

    lateinit var binding: ActivityResetPasswordBinding

    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            reset_password.observe(this@ResetPasswordActivity, { responseResetPassword(it) })
            logout.observe(this@ResetPasswordActivity, { responseLogout(it) })
            error.observe(this@ResetPasswordActivity, { showError(this@ResetPasswordActivity, it) })
            loading.observe(this@ResetPasswordActivity, { isLoading(it) })
        }
    }

    private fun responseLogout(it: ResponseLogout?) {
        if (it?.message == "success") {
            sessionManager.logout()

            val intent = Intent(this, LoginDaftarActivity::class.java)
            intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)

        } else {
            binding.root.showSnackbar(it?.message.toString(), Snackbar.LENGTH_LONG)
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

    private fun responseResetPassword(it: ResponseReqTopup?) {
        if (it?.code == "200") {

            sessionManager.flag_reset_password = false

//            if (sessionManager.id != "") {
                val param = RequestLogout(sessionManager.id)
                viewModel.logout(param)
//            }

            finish()

            showToast(this, "Ubah password berhasil")

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }

    }


    private fun initView() {

        with(binding) {

            imageButtonBack.setOnClickListener {
                sessionManager.flag_reset_password = false
                onBackPressed()
            }

            buttonResetPassword.setOnClickListener {
                if (TextUtils.isEmpty(editTextPassword.text.toString())) {
                    editTextPassword.error = getString(R.string.password_empty)
                }
                else if (TextUtils.isEmpty(editTextPassword2.text.toString())) {
                    editTextPassword2.error = getString(R.string.password_empty)
                }
                else if (editTextPassword.text.toString() != editTextPassword2.text.toString()) {
                    showToast(this@ResetPasswordActivity, getString(R.string.password_not_macth))
                } else {
                    var phone_number = ""
                    if (sessionManager.flag_reset_password) {
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
        sessionManager.flag_reset_password = false
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.flag_reset_password = false
    }
}