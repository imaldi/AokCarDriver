package com.surelabsid.mrjempootdriver.ui.orderan

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.DialogTerimaOrderanBinding
import com.surelabsid.mrjempootdriver.databinding.DialogTolakOrderBinding
import com.surelabsid.mrjempootdriver.databinding.FragmentOrderanBinding
import com.surelabsid.mrjempootdriver.ui.orderan.gocap.GocapAmbilFotoFingerActivity
import com.surelabsid.mrjempootdriver.ui.orderan.gopek.GopekAmbilFotoActivity
import com.surelabsid.mrjempootdriver.ui.orderan.goceng.GocengHargaPesananActivity
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import com.surelabsid.mrjempootdriver.ui.callandchat.ChatActivity
import com.surelabsid.mrjempootdriver.ui.orderan.adapter.OrderanAdapter
import com.surelabsid.mrjempootdriver.ui.orderan.adapter.ServiceAdapter
import com.surelabsid.mrjempootdriver.ui.orderan.goceng.GocengRatingActivity
import com.surelabsid.mrjempootdriver.ui.orderan.gokidz.GokidzLiveStreamingActivity
import com.surelabsid.mrjempootdriver.ui.orderan.gokidz.GokidzLiveStreamingActivityCopy
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestBayarKeAokCar
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.AMBIL_FOTO_FINGER_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MERCHANT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MR_JEMPOOT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.KIRIM_STRUK_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.LIVE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.CANCEL
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.COMPLETE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.DRIVER_FOUND
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.PROCESS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderanFragment : Fragment() {

    lateinit var binding: FragmentOrderanBinding

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var viewModel: OrderanViewModel

    private lateinit var dialog: Dialog

    private lateinit var dialogTolakOrderBinding: DialogTolakOrderBinding

    private lateinit var dialogTerimaOrderanBinding: DialogTerimaOrderanBinding

    private lateinit var adapter_orderan: OrderanAdapter

    private var list_orderan_local: List<EntityOrderan>? = null

    private var item_orderan: DataItem? = null

    private var alasan_tolak: String = ""

    private var first: Boolean = true

    private var default_button_name: String = ""

    private var serviceId: String? = null
    private var serviceIdOrderan: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serviceIdOrderan = arguments?.getString("service_id")
        Log.d("TAG", "onViewCreated: argument: $serviceId")

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)

        activity?.initPermissionCall()

        initPermissionLive()

        initView()

        attachObserve()

    }

    private fun initPermissionLive() {
        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                    LIVE
                )
            }
        }
    }

    private fun attachObserve() {
        with(viewModel) {
            service.observe(viewLifecycleOwner, { responseService(it) })
            transaction.observe(viewLifecycleOwner, { responseTransaction(it) })
            status_transaction.observe(viewLifecycleOwner, { responseStatusTransaction(it) })
            status_transaction_tolak_orderan.observe(
                viewLifecycleOwner,
                { responseStatusTransactionTolakOrderan(it) })
            status_transaction_jemput.observe(
                viewLifecycleOwner,
                { responseStatusTransactionJemput(it) })
            status_transaction_jemput_paket.observe(
                viewLifecycleOwner,
                { responseStatusTransactionJemputPaket(it) })
            status_transaction_ambil_paket.observe(
                viewLifecycleOwner,
                { responseStatusTransactionAmbilPaket(it) })
            status_transaction_antar_paket.observe(
                viewLifecycleOwner,
                { responseStatusTransactionAntarPaket(it) })
            status_transaction_paket_sampai.observe(
                viewLifecycleOwner,
                { responseStatusTransactionPaketSampai(it) })
            status_transaction_tagih_customer_paket.observe(
                viewLifecycleOwner,
                { responseStatusTransactionTagihCustomerPaket(it) })
            status_transaction_selesai_antar_paket.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSelesaiAntarPaket(it) })
            status_transaction_ambil_foto_finger.observe(
                viewLifecycleOwner,
                { responseStatusTransactionAmbilFotoFinger(it) })
            status_transaction_jemput_lagi.observe(
                viewLifecycleOwner,
                { responseStatusTransactionJemputLagi(it) })
            status_transaction_sudah_di_restoran.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSudahDiRestoran(it) })
            status_transaction_sudah_di_pesan.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSudahDiPesan(it) })
            status_transaction_bayar_merchant.observe(
                viewLifecycleOwner,
                { responseStatusTransactionBayarMerchant(it) })
            status_transaction_pesanan_sedang_diantar.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSedangDiantar(it) })
            status_transaction_pesanan_sudah_diantar.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSudahDiantar(it) })
            status_transaction_sama_customer.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSamaCustomer(it) })
            status_transaction_tujuan.observe(
                viewLifecycleOwner,
                { responseStatusTransactionTujuan(it) })
            status_transaction_selesai.observe(
                viewLifecycleOwner,
                { responseStatusTransactionSelesai(it) })
            status_transaction_bayar.observe(
                viewLifecycleOwner,
                { responseStatusTransactionBayarAokCar(it) })
            bayar_mr_jempoot.observe(
                viewLifecycleOwner,
                { responseBayarAokCar(it) })
            status_transaction_tagih.observe(
                viewLifecycleOwner,
                { responseStatusTransactionTagih(it) })
            loading.observe(
                viewLifecycleOwner,
                { showShimmerList(it, binding.shimmerFrameLayout, binding.listService) })
            loading_2.observe(
                viewLifecycleOwner,
                { showShimmerList(it, binding.shimmerFrameLayout2, binding.listOrderan) })
            loading_tolak_orderan.observe(viewLifecycleOwner, { loadingTolakOrderan(it) })
//            loading_button_process.observe(viewLifecycleOwner, { loadingTolakOrderan(it) })
            error.observe(viewLifecycleOwner, { showError(context, it) })

            add_orderan_local.observe(viewLifecycleOwner, { responseAddOrderantoLocal(it) })
            // todo: check the error of misplaced order here
            list_orderan_local.observe(viewLifecycleOwner, { responseListOrderanLocal(it) })
            update_button_name_orderan_local.observe(viewLifecycleOwner, { responseUpdateButtonNameOrderanLocal(it) })
        }
    }

//    private fun responseStatusTransactionKirimStrukCustomer(it: ResponseStatusTransaction?) {
//        if (it?.code == "200") {
//
//            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()
//
//            viewModel.updateButtonNameOrderanLocal(
//                sessionManager.temp_transaction_id.toInt(),
//                getString(R.string.pesanan_sedang_diantar)
//            )
//
//        } else {
//            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
//        }
//    }

    private fun responseUpdateButtonNameOrderanLocal(it: Boolean?) {
        if (it == true) {
            viewModel.getOrderanLocal()
        }
    }

    private fun responseListOrderanLocal(it: List<EntityOrderan>?) {

        if(first) {

            list_orderan_local = it
            // ini supaya ketika pertama kali ga kebuka ezzride tapi malah list ezzpick (kalau app sedang terbuka ada pesanan EzzPick)
            viewModel.transactionByServiceByStatus(sessionManager.id, 15, DRIVER_FOUND)
        } else {

            binding.rbOrderanDiterima.isChecked = true
            list_orderan_local = it

            viewModel.transactionByServiceByStatus(
                sessionManager.id,
                sessionManager.service_id,
                PROCESS
            )
        }

    }

    private fun responseAddOrderantoLocal(it: Boolean?) {
        if (it == true) {

            viewModel.getOrderanLocal()

        }
    }

    private fun loadingTolakOrderan(it: Boolean?) {
        if (it == true) {
            with(dialogTolakOrderBinding) {
                progressBar.visibility = View.VISIBLE
                buttonKirim.visibility = View.GONE
            }
        } else {
            with(dialogTolakOrderBinding) {
                progressBar.visibility = View.INVISIBLE
                buttonKirim.visibility = View.VISIBLE
            }
        }
    }

//    private fun loadingButtonProcess(it: Boolean?) {
//        if (it == true) {
//            with(dialogTolakOrderBinding) {
//                progressBar.visibility = View.VISIBLE
//                buttonKirim.visibility = View.GONE
//            }
//        } else {
//            with(dialogTolakOrderBinding) {
//                progressBar.visibility = View.INVISIBLE
//                buttonKirim.visibility = View.VISIBLE
//            }
//        }
//    }

    private fun responseStatusTransaction(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            showDialogTerimaOrderan()

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()
            val entityOrderan = EntityOrderan(
                null,
                sessionManager.temp_transaction_id.toInt(),
                PROCESS.toString(),
                default_button_name
            )
            viewModel.addOrderantoLocal(entityOrderan)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }

    }


    private fun showDialogTerimaOrderan() {
        dialogTerimaOrderanBinding = DialogTerimaOrderanBinding.bind(View.inflate(context, R.layout.dialog_terima_orderan, null))

        dialog = Dialog(binding.root.context).apply {
            setContentView(dialogTerimaOrderanBinding.root)
        }

        with(dialogTerimaOrderanBinding) {
            root.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun responseStatusTransactionTolakOrderan(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {
            dialog.dismiss()
            viewModel.transactionByServiceByStatus(
                sessionManager.id,
                sessionManager.service_id,
                DRIVER_FOUND
            )
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionJemput(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.sudah_sama_customer)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionJemputPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.udah_jemput_paket)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionAmbilPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.paket_sedang_dalam_perjalanan)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionAntarPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.paket_sudah_tiba_ditujuan)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionPaketSampai(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.selesai_antar_paket)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionTagihCustomerPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.selesai_antar_paket)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionSelesaiAntarPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.ambil_foto_dan_finger)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionAmbilFotoFinger(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.bayar_mr_jempoot)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionJemputLagi(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.sedang_dalam_perjalanan)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionSudahDiRestoran(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.orderan_sudah_di_pesan)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionSudahDiPesan(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.bayar_ke_merchant)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionSedangDiantar(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.pesanan_sudah_diantar)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseBayarAokCar(it: ResponseBayar?) {
        if (it?.code == 200) {
//            val data = it.data?.get(0)
//
//            item_orderan = it.data?.get(0)
//
//            val keterangan = getString(R.string.bayar_mr_jempoot)

            if (item_orderan?.walletPayment == "1") {

                binding.rbRiwayat.isChecked = true

                viewModel.transactionByServiceByStatus(
                    sessionManager.id,
                    sessionManager.service_id,
                    COMPLETE
                )
            } else {
                viewModel.updateButtonNameOrderanLocal(
                    sessionManager.temp_transaction_id.toInt(),
                    getString(R.string.tagih_customer)
                )
            }
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }



    private fun responseStatusTransactionSudahDiantar(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            val param = RequestBayarKeAokCar(
                sessionManager.id,
                it.data?.get(0)?.id ?: "",
                sessionManager.biaya_platform
            )
            viewModel.bayarKeAokCar(param)

//            if (item_orderan?.walletPayment == "1") {
//
//
//
//                binding.rbRiwayat.isChecked = true
//
//                viewModel.transactionByServiceByStatus(
//                    sessionManager.id,
//                    sessionManager.service_id,
//                    COMPLETE
//                )
//            } else {
//                viewModel.updateButtonNameOrderanLocal(
//                    sessionManager.temp_transaction_id.toInt(),
//                    getString(R.string.tagih_customer)
//                )
//            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }
    private fun responseStatusTransactionSamaCustomer(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id?.toInt() ?: 0,
                getString(R.string.menuju_tempat_tujuan)
            )
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionTujuan(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id?.toInt() ?: 0,
                getString(R.string.selesai_antar_tujuan)
            )
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionSelesai(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            var button_name = ""
            button_name = if(it.data?.get(0)?.trip == getString(R.string.pulang_pergi) && sessionManager.trip == "1") {
                getString(R.string.jemput_lagi_customer)
            } else {
                getString(R.string.bayar_mr_jempoot)
            }

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                button_name
            )
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionBayarAokCar(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            if (item_orderan?.walletPayment == "1") {

                binding.rbRiwayat.isChecked = true

                viewModel.updateButtonNameOrderanLocal(
                    sessionManager.temp_transaction_id.toInt(),
                    getString(R.string.bayar_mr_jempoot)
                )

                viewModel.transactionByServiceByStatus(
                    sessionManager.id,
                    sessionManager.service_id,
                    COMPLETE
                )

            } else {
                viewModel.updateButtonNameOrderanLocal(
                    sessionManager.temp_transaction_id.toInt(),
                    getString(R.string.tagih_customer)
                )
            }



        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
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


    private fun responseStatusTransactionTagih(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            binding.rbRiwayat.isChecked = true

            viewModel.transactionByServiceByStatus(
                sessionManager.id,
                sessionManager.service_id,
                COMPLETE
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }



    private fun responseTransaction(it: ResponseTransactionByService?) {
        try {
            if (it?.code == "200") {

                first = false

                adapter_orderan = OrderanAdapter(
                    binding.root.context,
                    it.data,
                    list_orderan_local,
                    object : OrderanAdapter.OnClickListener {
                        override fun itemClick(item: DataItem?) {

                        }

                        override fun itemClickNavigation(item: DataItem?, button_name: String) {

                            var startLatitude = "0.0"
                            var startLongitude = "0.0"

                            var endLatitude = "0.0"
                            var endLongitude = "0.0"

                            if (button_name == getString(R.string.menuju_lokasi_penjemputan)) {
                                startLatitude = sessionManager.lat_user
                                startLongitude = sessionManager.lng_user

                                endLatitude = item?.startLatitude ?: "0.0"
                                endLongitude = item?.startLongitude ?: "0.0"
                            } else if (button_name == getString(R.string.sudah_sama_customer)) {
                                startLatitude = sessionManager.lat_user
                                startLongitude = sessionManager.lng_user

                                endLatitude = item?.endLatitude ?: "0.0"
                                endLongitude = item?.endLongitude ?: "0.0"
                            } else if (button_name == getString(R.string.menuju_tempat_tujuan)) {
                                startLatitude = item?.startLatitude ?: "0.0"
                                startLongitude = item?.startLongitude ?: "0.0"

                                endLatitude = item?.endLatitude ?: "0.0"
                                endLongitude = item?.endLongitude ?: "0.0"
                            } else if (button_name == getString(R.string.selesai_antar_tujuan)) {
                                startLatitude = item?.startLatitude ?: "0.0"
                                startLongitude = item?.startLongitude ?: "0.0"

                                endLatitude = item?.endLatitude ?: "0.0"
                                endLongitude = item?.endLongitude ?: "0.0"
                            } else if (button_name == getString(R.string.bayar_mr_jempoot)) {
                                startLatitude = item?.startLatitude ?: "0.0"
                                startLongitude = item?.startLongitude ?: "0.0"

                                endLatitude = item?.endLatitude ?: "0.0"
                                endLongitude = item?.endLongitude ?: "0.0"
                            } else if (button_name == getString(R.string.tagih_customer)) {
                                startLatitude = item?.startLatitude ?: "0.0"
                                startLongitude = item?.startLongitude ?: "0.0"

                                endLatitude = item?.endLatitude ?: "0.0"
                                endLongitude = item?.endLongitude ?: "0.0"
                            }



                            val intent = Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=$startLatitude,$startLongitude&daddr=$endLatitude,$endLongitude"))
                            startActivity(intent)
                        }

                        override fun itemClickCancel(item: DataItem?, button_name: String) {
                            showDialogTolakOrder(item)
                        }

                        override fun itemClickTerima(item: DataItem?, button_name: String) {

                            sessionManager.temp_transaction_id = item?.id.toString()

                            default_button_name = button_name

                            val param = RequestStatusTransaction(
                                sessionManager.id,
                                item?.id.toString(),
                                PROCESS.toString(),
                                getString(
                                    R.string.accept_order
                                )
                            )
                            viewModel.transactionStatus(param)
                        }

                        override fun itemClickGocengMasukTolak(item: DataItem?) {
                            showDialogTolakOrder(item)
                        }

                        override fun itemClickGocengProcess(item: DataItem?, button_name: String) {

                            sessionManager.temp_transaction_id = item?.id.toString()

                            var keterangan = ""
                            if (button_name == getString(R.string.menuju_lokasi_penjemputan)) {
                                sessionManager.trip = "1"
                                keterangan = getString(R.string.menuju_lokasi_penjemputan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusJemput(param)
                            } else if (button_name == getString(R.string.sudah_sama_customer)) {
                                keterangan = getString(R.string.sudah_sama_customer)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusSamaCustomer(param)
                            } else if (button_name == getString(R.string.menuju_tempat_tujuan)) {
                                keterangan = getString(R.string.menuju_tempat_tujuan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusTujuan(param)
                            } else if (button_name == getString(R.string.selesai_antar_tujuan)) {
                                keterangan = getString(R.string.selesai_antar_tujuan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusSelesai(param)
                            } else if (button_name == getString(R.string.jemput_lagi_customer)) {
                                sessionManager.trip = "2"
                                keterangan = getString(R.string.jemput_lagi_customer)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusJemputLagi(param)
                            } else if (button_name == getString(R.string.sedang_dalam_perjalanan)) {
                                keterangan = getString(R.string.sedang_dalam_perjalanan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusJemput(param)
                            } else if (button_name == getString(R.string.bayar_mr_jempoot)) {

                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }

                                val intent = Intent(context, GocengHargaPesananActivity::class.java)
                                intent.putExtras(bundle)
                                startActivityForResult(intent, BAYAR_MR_JEMPOOT_RESULT)

                            } else if (button_name == getString(R.string.tagih_customer)) {
                                keterangan = getString(R.string.tagih_customer)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    COMPLETE.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusTagih(param)
                            } else {
                                Log.d( "itemClick: ", "masuk kesini")
                            }

                        }

                        override fun itemClickGopekProcess(item: DataItem?, button_name: String) {
                            sessionManager.temp_transaction_id = item?.id.toString()

                            var keterangan = ""
                            if (button_name == getString(R.string.saya_sudah_di_restoran)) {
                                keterangan = getString(R.string.saya_sudah_di_restoran)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusSudahDiRestoran(param)
                            } else if (button_name == getString(R.string.orderan_sudah_di_pesan)) {
                                keterangan = getString(R.string.orderan_sudah_di_pesan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusSudahDiPesan(param)
                            } else if (button_name == getString(R.string.bayar_ke_merchant)) {

                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }

                                val intent = Intent(context, GocengHargaPesananActivity::class.java)
                                intent.putExtra("bayar_merchant", true)
                                intent.putExtras(bundle)
                                startActivityForResult(intent, BAYAR_MR_JEMPOOT_RESULT)

                            } else if (button_name == getString(R.string.kirim_struk_ke_customer)) {

                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }

                                val intent = Intent(context, GopekAmbilFotoActivity::class.java)
                                intent.putExtras(bundle)
                                startActivityForResult(intent, KIRIM_STRUK_RESULT)

                            } else if (button_name == getString(R.string.pesanan_sedang_diantar)) {
                                item_orderan = item
                                keterangan = getString(R.string.pesanan_sedang_diantar)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusPesananSedangDiantar(param)
                            } else if (button_name == getString(R.string.pesanan_sudah_diantar)) {
                                item_orderan = item
                                keterangan = getString(R.string.pesanan_sudah_diantar)
                                var status_orderan = ""
                                // FIXME kenapa wallet payment??? sementara hilangkan saja dulu ini
                                if (item?.walletPayment == "1") {
                                    status_orderan = COMPLETE.toString()
                                } else {
                                    status_orderan = PROCESS.toString()
                                }

                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    status_orderan,
                                    keterangan
                                )
                                Log.d("statustrx", param.toString())
                                viewModel.transactionStatusPesananSudahDiantar(param)

                            } else if (button_name == getString(R.string.tagih_customer)) {
                                keterangan = getString(R.string.tagih_customer)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    COMPLETE.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusTagih(param)
                            } else {
                                Log.d( "itemClick: ", "masuk kesini")
                            }
                        }

                        override fun itemClickGocapProcess(item: DataItem?, button_name: String) {
                            sessionManager.temp_transaction_id = item?.id.toString()

                            var keterangan = ""
                            if (button_name == getString(R.string.menuju_lokasi_penjemputan)) {
                                keterangan = getString(R.string.menuju_lokasi_penjemputan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusJemputPaket(param)
                            } else if (button_name == getString(R.string.udah_jemput_paket)) {
                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }
                                val intent = Intent(context, GocapAmbilFotoFingerActivity::class.java)
                                intent.putExtras(bundle)
                                startActivityForResult(intent, AMBIL_FOTO_FINGER_RESULT)

                            } else if (button_name == getString(R.string.paket_sedang_dalam_perjalanan)) {
                                item_orderan = item
                                keterangan = getString(R.string.paket_sedang_dalam_perjalanan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusAntarPaket(param)
                            } else if (button_name == getString(R.string.paket_sudah_tiba_ditujuan)) {
                                item_orderan = item
                                keterangan = getString(R.string.paket_sudah_tiba_ditujuan)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusPaketSampai(param)
                            } else if (button_name == getString(R.string.selesai_antar_paket)) {
                                item_orderan = item
                                keterangan = getString(R.string.selesai_antar_paket)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    PROCESS.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusSelesaiAntarPaket(param)
                            } else if (button_name == getString(R.string.ambil_foto_dan_finger)) {

                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }

                                val intent = Intent(context, GocapAmbilFotoFingerActivity::class.java)
                                intent.putExtras(bundle)
                                intent.putExtra("ambil_foto_finger", true)
                                startActivityForResult(intent, AMBIL_FOTO_FINGER_RESULT)

                            } else if (button_name == getString(R.string.bayar_mr_jempoot)) {

                                val bundle = Bundle().apply {
                                    putSerializable("item_orderan", item)
                                }

                                val intent = Intent(context, GocengHargaPesananActivity::class.java)
                                intent.putExtras(bundle)
                                startActivityForResult(intent, BAYAR_MR_JEMPOOT_RESULT)

                            } else if (button_name == getString(R.string.tagih_customer)) {
                                item_orderan = item
                                keterangan = getString(R.string.tagih_customer)
                                val param = RequestStatusTransaction(
                                    sessionManager.id,
                                    item?.id.toString(),
                                    COMPLETE.toString(),
                                    keterangan
                                )
                                viewModel.transactionStatusTagih(param)
                            } else {
                                Log.d( "itemClick: ", "masuk kesini")
                            }
                        }

                        override fun itemClickNilai(item: DataItem?) {

                            sessionManager.temp_transaction_id = item?.id.toString()

                            val bundle = Bundle()
                            bundle.putSerializable("item", item)
                            val intent = Intent(context, GocengRatingActivity::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }

                        override fun itemClickChat(item: DataItem?) {
                            val bundle = Bundle()
                            bundle.putSerializable("item", item)
                            val intent = Intent(context, ChatActivity::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }

                        override fun itemClickCall(item: DataItem?) {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:" + "+"+item?.receiverPhone)
                            startActivity(callIntent)
                        }

                        override fun itemClickLive(item: DataItem?) {
                            activity?.openActivity(GokidzLiveStreamingActivityCopy::class.java)
                        }

                    })

                if (it.data?.size?.compareTo(0) ?: 0 > 0) {
                    val viewOrderan = "${it.data?.get(0)?.serviceId}${it.data?.get(0)?.status}"
                    adapter_orderan.setCurrentViewType(viewOrderan.toInt())
                }

                binding.listOrderan.adapter = adapter_orderan

            } else {
                binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
            }

        } catch (e: Exception) {
            AlertDialog.Builder(context).apply {
                setMessage("Something error with data")
                setNeutralButton("Okay") { dialog, which ->
                    dialog.dismiss()
                }
            }.show()

        }
    }

    private fun responseService(it: ResponseService?) {
        if (it?.code == "200") {

            if (serviceIdOrderan == null) {
                serviceId = it.data?.get(0)?.serviceId
            } else {
                serviceId = serviceIdOrderan
                sessionManager.service_id = serviceIdOrderan?.toInt() ?: 0
            }

            viewModel.transactionByServiceByStatus(
                sessionManager.id,
                serviceId?.toInt() ?: 0,
                DRIVER_FOUND
            )

            val serviceList = listOf<DataItemService>(
                it.data?.get(0) ?: DataItemService(),
                it.data?.get(2) ?: DataItemService(),
                it.data?.get(3) ?: DataItemService(),
            )

            val adapter = ServiceAdapter(
                binding.root.context,
                serviceList,
                object : ServiceAdapter.OnClickListener {
                    override fun itemClick(item: DataItemService?) {
                        sessionManager.service_id = item?.serviceId?.toInt() ?: 0
                        viewModel.transactionByServiceByStatus(
                            sessionManager.id,
                            sessionManager.service_id,
                            DRIVER_FOUND
                        )
                        binding.rbOrderanMasuk.isChecked = true
                    }

                })

            binding.listService.adapter = adapter

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        with(viewModel) {
            getService()

            viewModel.getOrderanLocal()

            binding.rbOrderanMasuk.setOnClickListener {
                transactionByServiceByStatus(
                    sessionManager.id,
                    sessionManager.service_id,
                    DRIVER_FOUND
                )
            }

            binding.rbOrderanDiterima.setOnClickListener {
                transactionByServiceByStatus(sessionManager.id, sessionManager.service_id, PROCESS)
            }

            binding.rbRiwayat.setOnClickListener {
                transactionByServiceByStatus(sessionManager.id, sessionManager.service_id, COMPLETE)
            }
        }

    }

    private fun showDialogTolakOrder(item: DataItem?) {
        dialogTolakOrderBinding =
            DialogTolakOrderBinding.bind(View.inflate(context, R.layout.dialog_tolak_order, null))

        dialog = Dialog(binding.root.context).apply {
            setContentView(dialogTolakOrderBinding.root)
        }

        dialog.show()

        with(dialogTolakOrderBinding) {
            radioButton1.setOnClickListener {
                alasan_tolak = radioButton1.text.toString()
                textLainnya.visibility = View.GONE
            }
            radioButton2.setOnClickListener {
                alasan_tolak = radioButton2.text.toString()
                textLainnya.visibility = View.GONE
            }
            radioButton3.setOnClickListener {
                alasan_tolak = radioButton3.text.toString()
                textLainnya.visibility = View.GONE
            }
            radioButton4.setOnClickListener {
                alasan_tolak = radioButton4.text.toString()
                textLainnya.visibility = View.GONE
            }
            radioButton5.setOnClickListener {
                alasan_tolak = textLainnya.text.toString()
                textLainnya.visibility = View.VISIBLE
            }

            textLainnya.doAfterTextChanged {
                alasan_tolak = it.toString()
            }

            buttonKirim.setOnClickListener {
                if (radioButton5.isChecked && TextUtils.isEmpty(textLainnya.text.toString())) {
                    textLainnya.error = getString(R.string.reject_reason_empty)
                } else {
                    val param = RequestStatusTransaction(
                        sessionManager.id,
                        item?.id.toString(),
                        CANCEL.toString(),
                        alasan_tolak
                    )
                    viewModel.transactionTolakOrderan(param)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AMBIL_FOTO_FINGER_RESULT || requestCode == BAYAR_MR_JEMPOOT_RESULT || requestCode == BAYAR_MERCHANT_RESULT || requestCode == KIRIM_STRUK_RESULT) {
            val is_success = data?.getBooleanExtra("is_success", false)
            if (is_success == true) {
                viewModel.getOrderanLocal()
            }
        }
    }

}