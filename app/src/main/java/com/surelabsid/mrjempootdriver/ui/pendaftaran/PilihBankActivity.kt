package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityPilihBankBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.adapter.ListBankAdapter
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseListBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BANK_RESULT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PilihBankActivity : AppCompatActivity() {

    lateinit var binding: ActivityPilihBankBinding

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var viewModel: PendaftaranViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            list_bank.observe(this@PilihBankActivity, { showListBank(it) })
            error.observe(this@PilihBankActivity, { showError(this@PilihBankActivity, it) })
            loading.observe(
                this@PilihBankActivity,
                { showShimmerList(it, binding.shimmerFrameLayout, binding.listBank) })
        }
    }

    private fun showListBank(responseListBank: ResponseListBank) {

        if (responseListBank.code == "200") {
            val adapter = ListBankAdapter(
                this,
                responseListBank.data,
                object : ListBankAdapter.OnClickListener {
                    override fun itemClick(item: DataItem?) {
                        val intent = Intent(this@PilihBankActivity, InformasiBankActivity::class.java)
                        intent.putExtra("id_bank", item?.id.toString())
                        intent.putExtra("nama_bank", item?.nameBank.toString())
                        setResult(BANK_RESULT, intent)
                        finish()
                    }

                })

            binding.listBank.adapter = adapter

        } else {
            binding.root.showSnackbar(
                "${responseListBank.code} - ${responseListBank.message}",
                Snackbar.LENGTH_LONG
            )
        }

    }

    private fun initView() {

        with(viewModel) {
            listBank()
        }


    }
}