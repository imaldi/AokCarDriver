package com.surelabsid.mrjempootdriver.ui.orderan.goceng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGocengHargaPesananBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MERCHANT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MR_JEMPOOT_RESULT
import com.surelabsid.mrjempootdriver.utils.moveActivity
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.surelabsid.mrjempootdriver.utils.toRupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GocengHargaPesananActivity : BaseActivity() {

    private lateinit var binding: ActivityGocengHargaPesananBinding

    private var item_orderan: DataItem? = null

    private var bayar_merchant: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGocengHargaPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item_orderan = intent.getSerializableExtra("item_orderan") as DataItem
        bayar_merchant = intent.getBooleanExtra("bayar_merchant", false)

        initView()

    }

    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            var subtotal = 0
            val biaya_platform = sessionManager.biaya_platform
            var ongkir = ""
            var total = ""

            if (bayar_merchant){
                ongkir = (item_orderan?.finalCost?.toInt()?.minus(biaya_platform.toInt())?.minus(item_orderan?.totalBelanja?.toInt() ?: 0)).toString()
                subtotal = (item_orderan?.finalCost?.toInt()?.minus(biaya_platform.toInt())?.minus(ongkir.toInt()) ?: 0)
            } else {
                ongkir = (item_orderan?.finalCost?.toInt()?.minus(biaya_platform.toInt())).toString()
                subtotal = (item_orderan?.finalCost?.toInt()?.minus(biaya_platform.toInt())?.minus(ongkir.toInt()) ?: 0)
            }
            textName.text = sessionManager.full_name

            if (subtotal <= 0) {
                layoutSubtotalPesanan.visibility = View.GONE

            } else {
                layoutSubtotalPesanan.visibility = View.VISIBLE
                textSubtotal.text = toRupiah(subtotal.toDouble())
            }

            textBiayaPlatform.text = toRupiah(biaya_platform.toDouble())
            textBiayaOngkir.text = toRupiah(ongkir.toDouble())
            textTotal.text = toRupiah(item_orderan?.finalCost?.toDouble())

            buttonKonfirmasi.setOnClickListener {
                val intent = Intent(this@GocengHargaPesananActivity, GocengKirimHargaActivity::class.java)
                intent.putExtra("id_orderan", item_orderan?.id)
                intent.putExtra("total_bayar_merchant", item_orderan?.totalBelanja)
                intent.putExtra("total_bayar_mr_jempoot", item_orderan?.totalBelanja)
                intent.putExtra("bayar_merchant", bayar_merchant)
                startActivityForResult(intent, BAYAR_MR_JEMPOOT_RESULT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BAYAR_MR_JEMPOOT_RESULT) {
            val is_success = data?.getBooleanExtra("is_success", false)
            if (is_success == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("is_success", true)
                setResult(BAYAR_MR_JEMPOOT_RESULT, intent)
                finish()
            }
        }
    }
}