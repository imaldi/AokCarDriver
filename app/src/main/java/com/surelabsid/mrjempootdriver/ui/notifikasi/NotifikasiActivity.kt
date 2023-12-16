package com.surelabsid.mrjempootdriver.ui.notifikasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityNotifikasiBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.notifikasi.adapter.NotifikasiAdapter
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.DataItemNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseDelNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.viewmodel.NotifikasiViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_NOTIFIKASI
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar

class NotifikasiActivity : BaseActivity() {

    private lateinit var binding: ActivityNotifikasiBinding

    private lateinit var viewModel: NotifikasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NotifikasiViewModel::class.java)

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            list_notifikasi.observe(this@NotifikasiActivity, { responseListNotifikasi(it) })
            del_notifikasi.observe(this@NotifikasiActivity, { responseDelNotifikasi(it) })
            loading.observe(this@NotifikasiActivity, {} )
            error.observe(this@NotifikasiActivity, { showError(this@NotifikasiActivity, it) })
        }
    }

    private fun responseDelNotifikasi(it: ResponseDelNotifikasi?) {
        if (it?.code == "200") {
            viewModel.listNotifikasi(sessionManager.id)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseListNotifikasi(it: ResponseNotifikasi?) {
        if (it?.code == "200") {
            val adapter = NotifikasiAdapter(this, it.data, object : NotifikasiAdapter.OnClickListener {
                override fun itemClick(item: DataItemNotifikasi?) {

                }

            })

            adapter.setCurrentViewType(ITEM_NOTIFIKASI)
            binding.recyclerView2.adapter = adapter

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        viewModel.listNotifikasi(sessionManager.id)

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            textView11.setOnClickListener {
                viewModel.delNotifikasi(sessionManager.id)
            }
        }
    }
}