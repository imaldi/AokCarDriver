package com.surelabsid.mrjempootdriver.ui.topup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityTopUpBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.HistoryWalletAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_TOPUP
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET_EMPTY
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.wallet_status.SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class TopUpActivity : BaseActivity() {

    lateinit var binding: ActivityTopUpBinding

    private var current_date_param: String? = null
    private var yesterday_date_param: String? = null

    private var current_date: String? = null
    private var yesterday_date: String? = null

    private var current_day: String? = null

    private var today_history: MutableList<WalletItem>? = null
    private var yesterday_history: MutableList<WalletItem>? = null

    lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        getCurrentDate()

        initView()

        attachObserve()


    }

    private fun attachObserve() {
        with(viewModel) {
            history_wallet.observe(this@TopUpActivity, { responseHistoryWallet(it) })
            error.observe(this@TopUpActivity, { showError(this@TopUpActivity, it) })
            loading.observe(this@TopUpActivity, {
                showShimmerList(it, binding.shimmerFrameLayout, binding.recyclerViewHariIni)
                showShimmerList(it, binding.shimmerFrameLayout2, binding.recyclerViewKemaren)
            })
        }
    }

    private fun responseHistoryWallet(it: ResponseHistoryWallet?) {
        if (it?.code == "200") {

            today_history = ArrayList()
            yesterday_history = ArrayList()

            today_history?.clear()
            yesterday_history?.clear()

            val item = it.data
            //update session
            with(sessionManager) {
                balance = item?.balance?.total.toString()
            }


            item?.wallet?.forEach {
                if (it?.date?.contains(current_date_param ?: "") == true && it.status == SUCCESS.toString()) {
                    today_history?.add(it)
                } else if (it?.date?.contains(yesterday_date_param ?: "") == true && it.status == SUCCESS.toString()){
                    (yesterday_history as ArrayList<WalletItem>).add(it)
                }
            }

            val adapter = applicationContext?.let { it1 -> HistoryWalletAdapter(it1, today_history) }
            val adapter_yesterday = applicationContext?.let { it1 -> HistoryWalletAdapter(it1, yesterday_history) }

            adapter?.setCurrentViewType(ITEM_TOPUP)
            adapter_yesterday?.setCurrentViewType(ITEM_TOPUP)

            if (item?.wallet?.size == 0) {
                adapter?.setCurrentViewType(ITEM_WALLET_EMPTY)
            }

            with(binding) {
                recyclerViewHariIni.adapter = adapter
                recyclerViewKemaren.adapter = adapter_yesterday
                textView20.text = toRupiah(sessionManager.balance.toDouble())

            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }


    private fun initView() {

        with(viewModel) {
            historyWalletByDate(sessionManager.id, yesterday_date_param, current_date_param)
        }

        with(binding) {
            textNamaUser.text = sessionManager.full_name
            textPhoneNumberUser.text = "+${sessionManager.username}"

            dayToday.text = current_day
            dateToday.text = current_date

            textView20.text = toRupiah(sessionManager.balance.toDouble())

            textView15.text = current_date
            textView17.text = yesterday_date

            imageButton4.setOnClickListener {
                openActivity(TopUpSaldoActivity::class.java)
            }
        }
    }

    private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        val calendar_yesterday = Calendar.getInstance().apply {
            add(Calendar.DATE, -1)
        }

        val date = calendar.time
        val yesterday = calendar_yesterday.time

        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        val simpleDateFormatParam = SimpleDateFormat("yyyy-MM-dd")
        val simpleDateFormatDay = SimpleDateFormat("EEE")

        current_date = simpleDateFormat.format(date)
        yesterday_date = simpleDateFormat.format(yesterday)

        current_day = simpleDateFormatDay.format(date)

        current_date_param = simpleDateFormatParam.format(date)
        yesterday_date_param = simpleDateFormatParam.format(yesterday)

    }

    override fun onResume() {
        super.onResume()
        initView()
    }
}