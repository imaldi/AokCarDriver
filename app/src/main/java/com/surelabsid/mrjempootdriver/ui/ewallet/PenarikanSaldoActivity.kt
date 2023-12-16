package com.surelabsid.mrjempootdriver.ui.ewallet

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPenarikanSaldoBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.RekeningAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.pendaftaran.InformasiBankActivity
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_REKENING

class PenarikanSaldoActivity : BaseActivity() {

    private lateinit var binding: ActivityPenarikanSaldoBinding

    private lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenarikanSaldoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        iniView()

        attachObserve()

    }

    private fun attachObserve() {

        with(viewModel) {
            history_wallet.observe(this@PenarikanSaldoActivity, { responseHistoryWallet(it)})
            error.observe(this@PenarikanSaldoActivity, { showError(this@PenarikanSaldoActivity, it) })
            loading.observe(this@PenarikanSaldoActivity, { showShimmerList(it, binding.shimmerFrameLayout, binding.listRekening) })
        }

    }

    private fun iniView() {

        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            imageButton.setOnClickListener {
                openActivity(InformasiBankActivity::class.java)
            }

            tarikSemua.setOnClickListener {
                textNominal.setText(sessionManager.balance)
            }
        }

        with(viewModel) {
            historyWallet(sessionManager.id)
        }

    }

    private fun responseHistoryWallet(it: ResponseHistoryWallet?) {
        if (it?.code == "200") {

            val item = it.data
            //update session
            with(sessionManager) {
                balance = item?.balance?.total.toString()
            }

            val adapter = applicationContext?.let { it1 -> RekeningAdapter(it1, item?.rekbank, object : RekeningAdapter.OnClickListener {
                override fun itemClick(item: RekbankItem?) {

                    if (TextUtils.isEmpty(binding.textNominal.text.toString())) {
                        binding.textNominal.error  = getString(R.string.nominal_empty)
                    } else {
                        val balance = sessionManager.balance.toInt()
                        val nominal_tarik = binding.textNominal.text.toString().toInt()

                        if (nominal_tarik > balance) {
                            binding.root.showSnackbar(getString(R.string.nominal_lebih), Snackbar.LENGTH_SHORT)
                        } else {
                            val intent = Intent(this@PenarikanSaldoActivity, PasswordActivity::class.java)
                            intent.putExtra("amount", binding.textNominal.text.toString())
                            intent.putExtra("id_rek", item?.id.toString())
                            startActivity(intent)
                            finish()
                        }
                    }
                }

            }) }

            adapter?.setCurrentViewType(ITEM_REKENING)

            with(binding) {

                textSaldo.text = toRupiah(sessionManager.balance.toDouble())
                listRekening.adapter = adapter

            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }

    override fun onResume() {
        super.onResume()
        with(viewModel) {
            historyWallet(sessionManager.id)
        }
    }

}