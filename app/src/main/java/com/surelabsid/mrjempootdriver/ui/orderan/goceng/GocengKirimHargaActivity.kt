package com.surelabsid.mrjempootdriver.ui.orderan.goceng

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGocengKirimHargaBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestPostPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.viewmodel.JempootPoinViewModel
import com.surelabsid.mrjempootdriver.ui.orderan.gopek.GopekVerifikasiActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestBayarKeAokCar
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MR_JEMPOOT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.COMPLETE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.PROCESS
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GocengKirimHargaActivity : BaseActivity() {

    private lateinit var binding: ActivityGocengKirimHargaBinding

    private lateinit var viewModel: OrderanViewModel
    private lateinit var viewModelJempootPoin: JempootPoinViewModel

    private var bayar_merchant: Boolean = false
    private var id_orderan: String? = null
    private var total_bayar_merchant: String? = null
    private var total_bayar_mr_jempoot: String? = null

    private var item_orderan: DataItemBayar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGocengKirimHargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)
        viewModelJempootPoin = ViewModelProvider(this).get(JempootPoinViewModel::class.java)

        bayar_merchant = intent.getBooleanExtra("bayar_merchant", false)
        id_orderan = intent.getStringExtra("id_orderan").toString()
        total_bayar_merchant = intent.getStringExtra("total_bayar_merchant").toString()
        total_bayar_mr_jempoot = intent.getStringExtra("total_bayar_mr_jempoot").toString()

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            bayar_mr_jempoot.observe(this@GocengKirimHargaActivity, { responseBayarAokCar(it) })
            status_transaction_bayar.observe(this@GocengKirimHargaActivity, { responseStatusTransactionBayarAokCar(it) })
            transaction.observe(this@GocengKirimHargaActivity, { responseTransaction(it) })
            update_button_name_orderan_local.observe(
                this@GocengKirimHargaActivity,
                { responseUpdateButtonNameOrderanLocal(it) })
            loading.observe(this@GocengKirimHargaActivity, { isLoading(it) })
            error.observe(
                this@GocengKirimHargaActivity,
                { showError(this@GocengKirimHargaActivity, it) })
        }

        with(viewModelJempootPoin) {
            post_poin.observe(this@GocengKirimHargaActivity, { responsePostPoint(it) })
        }

    }

    private fun responsePostPoint(it: ResponsePoin?) {
        if (it?.code == "200") {
            val intent = Intent(this, GocengHargaPesananActivity::class.java)
            intent.putExtra("is_success", true)
            setResult(BAYAR_MR_JEMPOOT_RESULT, intent)
            finish()
        }
    }


    private fun isLoading(it: Boolean?) {
        if (it == true) {
            binding.buttonKirim.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.buttonKirim.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun responseBayarAokCar(it: ResponseBayar?) {
        if (it?.code == 200) {
            val data = it.data?.get(0)

            item_orderan = it.data?.get(0)

            val keterangan = getString(R.string.bayar_mr_jempoot)

            var status_orderan = ""
            if (data?.walletPayment == "1") {
                status_orderan = COMPLETE.toString()
            } else {
                status_orderan = PROCESS.toString()
            }

            val param = RequestStatusTransaction(
                sessionManager.id,
                data?.id.toString(),
                status_orderan,
                keterangan
            )
            viewModel.transactionStatusBayar(param)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionBayarAokCar(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            val item_orderan = it.data?.get(0)

            sessionManager.temp_transaction_id = item_orderan?.id.toString()

            if (item_orderan?.walletPayment == "1") {

//                viewModel.updateButtonNameOrderanLocal(
//                    sessionManager.temp_transaction_id.toInt(),
//                    getString(R.string.bayar_mr_jempoot)
//                )

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

    private fun responseTransaction(it: ResponseTransactionByService?) {
        if (it?.code == "200") {

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.bayar_mr_jempoot)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }


    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                binding.root.showSnackbar(
                    getString(R.string.selesaikan_pembayaran),
                    Snackbar.LENGTH_LONG
                )
            }

            if (bayar_merchant) {
                label.text =
                    getString(R.string.harap_masukkan_total_pesanan_yang_ada_di_struk_belanja)
                labelBiaya.text = getString(R.string.total_harga_pembelanjaan)
                labelKonfirmasi.text = getString(R.string.konfirmasi_total_harga_pembelanjaan)
                buttonKirim.text = getString(R.string.kirim_ke_merchant)

                buttonKirim.setOnClickListener {
                    if (TextUtils.equals(
                            textKonfirmasi.text.toString(),
                            textBiaya.text.toString()
                        )
                    ) {

                        if (textBiaya.text.toString() == total_bayar_merchant) {
                            val intent = Intent(
                                this@GocengKirimHargaActivity,
                                GopekVerifikasiActivity::class.java
                            )
                            intent.putExtra("id_orderan", id_orderan)
                            intent.putExtra("total_bayar_merchant", textBiaya.text.toString())
                            startActivityForResult(intent, BAYAR_MR_JEMPOOT_RESULT)
                        } else {
                            binding.root.showSnackbar(
                                getString(R.string.total_bayar_invalid),
                                Snackbar.LENGTH_SHORT
                            )
                        }
                    } else {
                        binding.root.showSnackbar(
                            getString(R.string.konfirmasi_pembelanjaan_invalid),
                            Snackbar.LENGTH_SHORT
                        )
                    }
                }

            }
            else {
                label.text = getString(R.string.harap_masukkan_total_biaya_platform)
                labelBiaya.text = getString(R.string.total_biaya_platform)
                labelKonfirmasi.text = getString(R.string.konfirmasi_total_biaya_platform)
                buttonKirim.text = getString(R.string.kirim_ke_AokCar)

                textBiaya.setText(sessionManager.biaya_platform)


                buttonKirim.setOnClickListener {
                    Log.d("textBiaya",textBiaya.text.toString())
                    Log.d("textKonfirmasi",textKonfirmasi.text.toString())
                    Log.d("isBiaya = Konf",TextUtils.equals(
                        textKonfirmasi.text.toString(),
                        textBiaya.text.toString()
                    ).toString())
                    if (

//                        textKonfirmasi.text.toString() == textBiaya.text.toString()
                        TextUtils.equals(
                            textKonfirmasi.text.toString(),
                            textBiaya.text.toString()
                        )
                    ) {
                        val param = RequestBayarKeAokCar(
                            sessionManager.id,
                            id_orderan ?: "",
                            sessionManager.biaya_platform
                        )
                        viewModel.bayarKeAokCar(param)

                    } else {
                        binding.root.showSnackbar(
                            getString(R.string.konfirmasi_biaya_invalid),
                            Snackbar.LENGTH_SHORT
                        )
                    }
                }

            }
        }
    }

    private fun responseUpdateButtonNameOrderanLocal(it: Boolean?) {
        if (it == true) {

            if (item_orderan?.poinCommision != null) {
                val param = RequestPostPoin(
                    sessionManager.id,
                    item_orderan?.poinCommision ?: "",
                    "Poin dari transaksi ${item_orderan?.service}"
                )

                viewModelJempootPoin.postPoin(param)
            } else {
                val intent = Intent(this, GocengHargaPesananActivity::class.java)
                intent.putExtra("is_success", true)
                setResult(BAYAR_MR_JEMPOOT_RESULT, intent)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BAYAR_MR_JEMPOOT_RESULT) {
            val is_success = data?.getBooleanExtra("is_success", false)
            if (is_success == true) {
                val intent = Intent(this, GocengHargaPesananActivity::class.java)
                intent.putExtra("is_success", true)
                setResult(BAYAR_MR_JEMPOOT_RESULT, intent)
                finish()
            }
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        binding.root.showSnackbar(getString(R.string.selesaikan_pembayaran), Snackbar.LENGTH_LONG)
//    }
}