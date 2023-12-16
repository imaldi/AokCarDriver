package com.surelabsid.mrjempootdriver.ui.topup

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityTopUpKonfirmasiBinding
import com.surelabsid.mrjempootdriver.databinding.DialogTopupBerhasilBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings
import com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel.WalletViewModel
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.wallet_status.PENDING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

@AndroidEntryPoint
class TopUpKonfirmasiActivity : BaseActivity() {

    private lateinit var binding: ActivityTopUpKonfirmasiBinding

    private lateinit var viewModel: WalletViewModel

    private lateinit var dialog: Dialog

    private lateinit var dialogTopupBerhasilBinding: DialogTopupBerhasilBinding

    private var amount = ""

    private lateinit var param: RequestReqTopup

    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpKonfirmasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionReadPhoneState()

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        amount = intent.getStringExtra("amount").toString()

        appSettings = Json.decodeFromString(sessionManager.app_settings)

        initializeSDK()

        initView()

        attachObserve()

    }

    private fun initializeSDK() {
        SdkUIFlowBuilder.init()
//            .setClientKey(appSettings.midtransClientKeySb)
            .setClientKey("SB-Mid-client-Vvt-lfkmuR_neS2f")
            .setContext(this)
            .setTransactionFinishedCallback {

            }
            .setMerchantBaseUrl("${BASE}/")
            .enableLog(true)
            .setLanguage("id")
            .buildSDK()
    }


    private fun attachObserve() {
        with(viewModel) {
            req_topup.observe(this@TopUpKonfirmasiActivity, { responseReqTopup(it) })
            loading.observe(this@TopUpKonfirmasiActivity, { isLoading(it) })
            error.observe(
                this@TopUpKonfirmasiActivity,
                { showError(this@TopUpKonfirmasiActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                button6.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                button6.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun responseReqTopup(it: ResponseReqTopup?) {
        if (it?.code == "200") {
//            showDialogBerhasilTopup()

            generateMidTrans(param)

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            textView57.text = toRupiah(amount.toDouble())

            button6.setOnClickListener {
                val h = HourToMillis()

                param = RequestReqTopup(
                    id = UUID.randomUUID().toString(),
                    date = h.millisToDateHour(h.millis(), null),
                    idUser = sessionManager.id,
                    type = "Topup",
                    walletAmount = amount.toDouble(),
                    status = PENDING
                )
                viewModel.reqTopup(param)
            }
        }
    }

    private fun generateMidTrans(walletrequest: RequestReqTopup) {
        val transactionRequest =
            TransactionRequest(
                walletrequest.id.toString(),
                walletrequest.walletAmount.toString().toDouble()
            )
        val customeDetails = CustomerDetails()
        val name = sessionManager.full_name.split(" ")
        val lastName: String
        var fistName: String
        if (name!!.size > 1) {
            lastName = name[name.size.minus(1)]
            fistName = ""
            for (i in 0 until name.size.minus(1)) {
                fistName += name[i] + " "
            }
        } else {
            lastName = name[name.size.minus(1)]
            fistName = name[name.size.minus(1)]
        }

        customeDetails.email = sessionManager.email
        customeDetails.firstName = fistName
        customeDetails.lastName = lastName
        customeDetails.phone = sessionManager.username
        customeDetails.customerIdentifier = sessionManager.id

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Yogyakarta"
        shippingAddress.city = "Yogyakarta"
        shippingAddress.postalCode = "55572"

        customeDetails.shippingAddress = shippingAddress

        val itemDetails = ItemDetails(
            walletrequest.id,
            walletrequest.walletAmount.toString().toDouble(),
            1,
            walletrequest.type
        )
        val itemDetailList = arrayListOf<ItemDetails>()
        itemDetailList.add(itemDetails)

        transactionRequest.customerDetails = customeDetails
        transactionRequest.itemDetails = itemDetailList

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)
        finish()
    }


    private fun showDialogBerhasilTopup() {
        dialogTopupBerhasilBinding = DialogTopupBerhasilBinding.bind(
            View.inflate(
                this,
                R.layout.dialog_topup_berhasil,
                null
            )
        )

        dialog = Dialog(this).apply {
            setContentView(dialogTopupBerhasilBinding.root)
        }

        dialog.show()

        dialogTopupBerhasilBinding.buttonOke.setOnClickListener {
            dialog.dismiss()
            finish()
        }

    }
}