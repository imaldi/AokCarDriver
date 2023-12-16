package com.surelabsid.mrjempootdriver.ui.orderan.goceng

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGocengRatingBinding
import com.surelabsid.mrjempootdriver.databinding.DialogSuccessNilaiCustomerBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestNilaiCustomer
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseNilaiCustomer
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.surelabsid.mrjempootdriver.utils.toRupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GocengRatingActivity : BaseActivity() {

    private lateinit var binding: ActivityGocengRatingBinding

    private lateinit var viewModel: OrderanViewModel

    private lateinit var dialog: Dialog

    private lateinit var dialogSuccessNilaiCustomerBinding: DialogSuccessNilaiCustomerBinding

    private var item: DataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGocengRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)

        item = intent.getSerializableExtra("item") as DataItem

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            nilai_customer.observe(this@GocengRatingActivity, { responseNilaiCustomer(it) })
            loading.observe(this@GocengRatingActivity, { isLoading(it) })
            error.observe(this@GocengRatingActivity, { showError(this@GocengRatingActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                buttonKirimUlasan.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                buttonKirimUlasan.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }


    private fun responseNilaiCustomer(it: ResponseNilaiCustomer?) {
        if (it?.code == "200") {
            showDialogSuccessNilaiCustomer()
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun showDialogSuccessNilaiCustomer() {
        dialogSuccessNilaiCustomerBinding = DialogSuccessNilaiCustomerBinding.bind(View.inflate(this, R.layout.dialog_success_nilai_customer, null))
        dialog = Dialog(this, R.style.CustomDialogTheme).apply {
            setContentView(dialogSuccessNilaiCustomerBinding.root)
        }

        with(dialogSuccessNilaiCustomerBinding) {
            buttonOke.setOnClickListener {
                dialog.dismiss()
                finish()
            }
        }

        dialog.show()
    }

    private fun initView() {
        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
            appCompatTextView21.text = "Orderan Selesai tgl ${formatDate(item?.date)}"
            textView76.text = toRupiah(item?.finalCost?.toDouble())
            textView79.text = toRupiah(item?.finalCost?.toDouble())

            buttonKirimUlasan.setOnClickListener {
                val param = RequestNilaiCustomer(item?.id.toString(), sessionManager.id, item?.customerId.toString(), buttonRating.rating.toString())
                viewModel.nilaiCustomer(param)
            }
        }
    }
}