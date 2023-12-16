package com.surelabsid.mrjempootdriver.ui.ewallet

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AlertDialogLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPasswordBinding
import com.surelabsid.mrjempootdriver.databinding.DialogWithdrawBerhasilBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.LoginViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.WITHDRAW_RESULT
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showShimmerList
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityPasswordBinding

    lateinit var viewModel: WalletViewModel

    lateinit var viewModelLogin: LoginViewModel

    private var amount: String = ""
    private var id_rek: String = ""

    private lateinit var dialog: Dialog

    private lateinit var dialogWithdrawBerhasilBinding: DialogWithdrawBerhasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)

        amount = intent.getStringExtra("amount").toString()
        id_rek = intent.getStringExtra("id_rek").toString()


        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModelLogin) {
            login.observe(this@PasswordActivity, { responseLogin(it)})
            error.observe(this@PasswordActivity, { showError(this@PasswordActivity, it) })
            loading.observe(this@PasswordActivity, { isLoading(it) })
        }

        with(viewModel) {
            req_withdraw.observe(this@PasswordActivity, { responseReqWithdraw(it)})
        }
    }

    private fun responseReqWithdraw(it: ResponseWithdraw?) {
        if (it?.code == "200") {
            showDialogWithdrawBerhasil()
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                buttonLanjutkan.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                buttonLanjutkan.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun responseLogin(it: ResponseLogin?) {
        if (it?.code == "200") {

            val param = RequestWithdraw(sessionManager.id, amount, id_rek)
            viewModel.reqWithdraw(param)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }

    private fun initView() {
        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            buttonLanjutkan.setOnClickListener {
                if (TextUtils.isEmpty(binding.editTextPassword.text.toString())) {
                    binding.editTextPassword.error = getString(R.string.password_empty)
                } else {
                    val param = RequestLogin(sessionManager.username, binding.editTextPassword.text.toString(), sessionManager.get_token)
                    viewModelLogin.login(param)
                }
            }
        }
    }

    private fun showDialogWithdrawBerhasil() {
        dialogWithdrawBerhasilBinding = DialogWithdrawBerhasilBinding.bind(View.inflate(this, R.layout.dialog_withdraw_berhasil, null))

        dialog = Dialog(this).apply {
            setContentView(dialogWithdrawBerhasilBinding.root)
        }

        dialog.show()

        dialogWithdrawBerhasilBinding.buttonOke.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }
}