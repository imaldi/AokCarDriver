package com.surelabsid.mrjempootdriver.ui.ewallet

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.DialogWithdrawBerhasilBinding
import com.surelabsid.mrjempootdriver.databinding.FragmentEWalletBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.HistoryWalletAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.WITHDRAW_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class EWalletFragment : Fragment() {

    lateinit var binding: FragmentEWalletBinding

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var viewModel: WalletViewModel

    private var all_wallet: MutableList<WalletItem>? = null
    private var in_wallet: MutableList<WalletItem>? = null
    private var out_wallet: MutableList<WalletItem>? = null

    private var start_date = ""
    private var end_date = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        initView()

        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            history_wallet.observe(viewLifecycleOwner, { responseHistoryWallet(it)})
            error.observe(viewLifecycleOwner, { showError(context, it)})
            loading.observe(viewLifecycleOwner, { showShimmerList(it, binding.shimmerFrameLayout, binding.listHistoryWallet) })
        }
    }

    private fun initView() {

        all_wallet = ArrayList<WalletItem>()
        in_wallet = ArrayList<WalletItem>()
        out_wallet = ArrayList<WalletItem>()

        listenerTabLayout()

        with(viewModel) {
            historyWallet(sessionManager.id)
        }

        with(binding) {

            appCompatButton.setOnClickListener {
                activity?.openActivity(PenarikanSaldoActivity::class.java)
            }

            riwayatPenarikan.setOnClickListener {
                activity?.openActivity(RiwayatPenarikanActivity::class.java)
            }

            textViewMulaiDari.setOnClickListener {
                val dateStart = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih tanggal mulai").build()
                activity?.supportFragmentManager?.let { it1 -> dateStart.show(it1, "MATERIAL_DATE_PICKER") }

                dateStart.addOnPositiveButtonClickListener {
                    textViewMulaiDari.text = dateStart.headerText
                    start_date = formatDatePicker(Date(it).toString())

                    if (start_date == "" || end_date == ""){
                        binding.root.showSnackbar(getString(R.string.date_range_empty), Snackbar.LENGTH_SHORT)
                    } else {
                        viewModel.historyWalletByDate(sessionManager.id, start_date, end_date)
                    }
                }
            }

            textViewSampai.setOnClickListener {
                val dateEnd = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih tanggal sampai").build()
                activity?.supportFragmentManager?.let { it1 -> dateEnd.show(it1, "MATERIAL_DATE_PICKER") }

                dateEnd.addOnPositiveButtonClickListener {
                    textViewSampai.text = dateEnd.headerText
                    end_date = formatDatePicker(Date(it).toString())

                    if (start_date == "" || end_date == ""){
                        binding.root.showSnackbar(getString(R.string.date_range_empty), Snackbar.LENGTH_SHORT)
                    } else {
                        viewModel.historyWalletByDate(sessionManager.id, start_date, end_date)
                    }
                }
            }

        }
    }

    private fun listenerTabLayout() {
        with(binding) {

            tabLayout.addTab(tabLayout.newTab().setText("Semua"))
            tabLayout.addTab(tabLayout.newTab().setText("Penghasilan"))
            tabLayout.addTab(tabLayout.newTab().setText("Pengeluaran"))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            listHistoryWallet.visibility = View.VISIBLE
                            listHistoryWalletIn.visibility = View.GONE
                            listHistoryWalletOut.visibility = View.GONE
                            android.util.Log.d("TAG", "onTabSelected: semua")
                        }
                        1 -> {
                            listHistoryWalletIn.visibility = View.VISIBLE
                            listHistoryWallet.visibility = View.GONE
                            listHistoryWalletOut.visibility = View.GONE
                            android.util.Log.d("TAG", "onTabSelected: penghasilan")
                        }
                        2 -> {
                            listHistoryWalletOut.visibility = View.VISIBLE
                            listHistoryWalletIn.visibility = View.GONE
                            listHistoryWallet.visibility = View.GONE
                            android.util.Log.d("TAG", "onTabSelected: pengeluaran")
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //
                }

            })
        }
    }

    private fun responseHistoryWallet(it: ResponseHistoryWallet?) {
        if (it?.code == "200") {

            all_wallet?.clear()
            in_wallet?.clear()
            out_wallet?.clear()

            val item = it.data
            //update session
            with(sessionManager) {
                balance = item?.balance?.total.toString()
            }

            item?.wallet?.forEach {
                if (it != null) {
                    all_wallet?.add(it)

                    if (it.type == "Order+") {
                        in_wallet?.add(it)
                    }
                    if (it.type == "Order-") {
                        out_wallet?.add(it)
                    }
                    if (it.type == "Topup") {
                        in_wallet?.add(it)
                    }
                    if (it.type == "Withdraw") {
                        out_wallet?.add(it)
                    }
                }
            }

            val adapter = context?.let { it1 -> HistoryWalletAdapter(it1, all_wallet) }
            val adapter_in = context?.let { it1 -> HistoryWalletAdapter(it1, in_wallet) }
            val adapter_out = context?.let { it1 -> HistoryWalletAdapter(it1, out_wallet) }

            adapter?.setCurrentViewType(ITEM_WALLET)
            adapter_in?.setCurrentViewType(ITEM_WALLET)
            adapter_out?.setCurrentViewType(ITEM_WALLET)

            with(binding) {
                textViewSaldo.text = toRupiah(sessionManager.balance.toDouble())

                val penghasilan = item?.totalorderplus?.total?.toDouble() ?: 0.0.plus(item?.totaltopup?.total?.toDouble() ?: 0.0)
                textViewPenghasilan.text = toRupiah(penghasilan)

                val pengeluaran = item?.totalordermin?.total?.toDouble() ?: 0.0.plus(item?.totalwithdraw?.total?.toDouble() ?: 0.0)
                textViewPengeluaran.text = toRupiah(pengeluaran)

                listHistoryWallet.adapter = adapter
                listHistoryWalletIn.adapter = adapter_in
                listHistoryWalletOut.adapter = adapter_out
            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }

}