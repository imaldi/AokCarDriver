package com.surelabsid.mrjempootdriver.ui.ewallet

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityRiwayatPenarikanBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.HistoryWalletAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_PENARIKAN

class RiwayatPenarikanActivity : BaseActivity() {

    private lateinit var binding: ActivityRiwayatPenarikanBinding

    lateinit var viewModel: WalletViewModel

    private var all_withdraw: MutableList<WalletItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatPenarikanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun initView() {

        all_withdraw = ArrayList()

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
        }

        with(viewModel) {
            historyWallet(sessionManager.id)
        }
    }

    private fun attachObserve() {
        with(viewModel) {
            history_wallet.observe(this@RiwayatPenarikanActivity, { responseHistoryWallet(it) })
            error.observe(
                this@RiwayatPenarikanActivity,
                { showError(this@RiwayatPenarikanActivity, it) })
            loading.observe(
                this@RiwayatPenarikanActivity,
                { showShimmerList(it, binding.shimmerFrameLayout, binding.listPenarikan) })
        }
    }

    private fun responseHistoryWallet(it: ResponseHistoryWallet?) {
        if (it?.code == "200") {

            all_withdraw?.clear()

            val item = it.data
            //update session
            with(sessionManager) {
                balance = item?.balance?.total.toString()
            }

            item?.wallet?.forEach {
                if (it != null) {
                    if (it.type == "Withdraw") {
                        all_withdraw?.add(it)
                    }
                }
            }

            val adapter = applicationContext?.let { it1 -> HistoryWalletAdapter(it1, all_withdraw) }

            adapter?.setCurrentViewType(ITEM_PENARIKAN)

            with(binding) {

                listPenarikan.adapter = adapter
            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }


}