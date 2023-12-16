package com.surelabsid.mrjempootdriver.ui.orderan.gopek

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGopekVerifikasiBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.orderan.goceng.GocengKirimHargaActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestBayarKeMerchant
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestVerifyCodeMerchant
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseBayar
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MERCHANT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MR_JEMPOOT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.PROCESS
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GopekVerifikasiActivity : BaseActivity() {

    private lateinit var binding: ActivityGopekVerifikasiBinding

    private lateinit var viewModel: OrderanViewModel

    private var id_orderan: String? = null
    private var total_bayar_merchant: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGopekVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)

        id_orderan = intent.getStringExtra("id_orderan").toString()
        total_bayar_merchant = intent.getStringExtra("total_bayar_merchant").toString()

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            verify_code_merchant.observe(
                this@GopekVerifikasiActivity,
                { responseVerifyCodeMerchant(it) })
            bayar_merchant.observe(this@GopekVerifikasiActivity, { responseBayarMerchant(it) })
            status_transaction_bayar_merchant.observe(
                this@GopekVerifikasiActivity,
                { responseStatusTransactionBayarMerchant(it) })
            update_button_name_orderan_local.observe(
                this@GopekVerifikasiActivity,
                { responseUpdateButtonNameOrderanLocal(it) })
            loading.observe(this@GopekVerifikasiActivity, { isLoading(it) })
            error.observe(this@GopekVerifikasiActivity, { showError(this@GopekVerifikasiActivity, it) })
        }

    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonLanjut.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.buttonLanjut.visibility = View.VISIBLE
        }
    }

    private fun responseUpdateButtonNameOrderanLocal(it: Boolean?) {
        if (it == true) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("is_success", true)
            setResult(BAYAR_MR_JEMPOOT_RESULT, intent)
            finish()
        }
    }


    private fun responseStatusTransactionBayarMerchant(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.kirim_struk_ke_customer)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }


    private fun responseVerifyCodeMerchant(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {
            val param = RequestBayarKeMerchant(
                sessionManager.id,
                id_orderan ?: "",
                total_bayar_merchant ?: ""
            )
            viewModel.bayarKeMerchant(param)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {

        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        binding.numone.doOnTextChanged { text, start, before, count ->
            when (count) {
                1 -> {
                    binding.numtwo.requestFocus()
                    binding.numtwo.selectAll()
                }
            }
        }

        binding.numtwo.doOnTextChanged { text, start, before, count ->
            when (count) {
                0 -> {
                    binding.numone.requestFocus()
                    binding.numone.selectAll()
                }
                1 -> {
                    binding.numthree.requestFocus()
                    binding.numthree.selectAll()

                }
            }
        }

        binding.numthree.doOnTextChanged { text, start, before, count ->
            when (count) {
                0 -> {
                    binding.numtwo.requestFocus()
                    binding.numtwo.selectAll()
                }
                1 -> {
                    binding.numfour.requestFocus()
                    binding.numfour.selectAll()
                }
            }
        }

        binding.numfour.doOnTextChanged { text, start, before, count ->
            when (count) {
                0 -> {
                    binding.numthree.requestFocus()
                    binding.numthree.selectAll()
                }
                1 -> {
                    //
                }
            }
        }

        binding.buttonLanjut.setOnClickListener {
            if (TextUtils.isEmpty(binding.numone.text) || TextUtils.isEmpty(binding.numtwo.text) || TextUtils.isEmpty(
                    binding.numthree.text
                ) || TextUtils.isEmpty(binding.numfour.text)
            ) {
                binding.root.showSnackbar(getString(R.string.pin_invalid), Snackbar.LENGTH_SHORT)
            } else {
                val code =
                    "${binding.numone.text}${binding.numtwo.text}${binding.numthree.text}${binding.numfour.text}"
                val param = RequestVerifyCodeMerchant(sessionManager.id, id_orderan ?: "", code)
                viewModel.verifyCodeMerchant(param)
            }
        }
    }

    private fun responseBayarMerchant(it: ResponseBayar?) {
        if (it?.code == 200) {

            val keterangan = getString(R.string.bayar_ke_merchant)

            val item = it.data?.get(0)

            val param = RequestStatusTransaction(
                sessionManager.id,
                item?.id.toString(),
                PROCESS.toString(),
                keterangan
            )
            viewModel.transactionStatusBayarMerchant(param)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

}