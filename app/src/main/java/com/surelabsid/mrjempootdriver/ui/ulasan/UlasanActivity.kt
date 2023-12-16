package com.surelabsid.mrjempootdriver.ui.ulasan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.persistableBundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityUlasanBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.ulasan.adapter.UlasanAdapter
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.DetailItem
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import com.surelabsid.mrjempootdriver.ui.ulasan.viewmodel.UlasanViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_ULASAN
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UlasanActivity : BaseActivity() {

    private lateinit var binding: ActivityUlasanBinding

    private lateinit var viewModel: UlasanViewModel

    private var list_rate_1: MutableList<DetailItem>? = null
    private var list_rate_2: MutableList<DetailItem>? = null
    private var list_rate_3: MutableList<DetailItem>? = null
    private var list_rate_4: MutableList<DetailItem>? = null
    private var list_rate_5: MutableList<DetailItem>? = null

    private lateinit var adapter: UlasanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUlasanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UlasanViewModel::class.java)

        initView()
        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            performance_driver.observe(this@UlasanActivity, { responsePerformaceDriver(it) })
            error.observe(this@UlasanActivity, { showError(this@UlasanActivity, it) })
        }
    }

    private fun responsePerformaceDriver(it: ResponsePerformanceDriver?) {
        if (it?.code == "200") {

            val data = it.data

            val count_rate_1 = data?.countRate?.rate1
            val count_rate_2 = data?.countRate?.rate2
            val count_rate_3 = data?.countRate?.rate3
            val count_rate_4 = data?.countRate?.rate4
            val count_rate_5 = data?.countRate?.rate5
            val count_rate_all = count_rate_1?.plus(count_rate_2 ?: 0)?.plus(count_rate_3 ?: 0)
                ?.plus(count_rate_4 ?: 0)?.plus(count_rate_5 ?: 0)

            data?.detail?.forEach {
                when (it?.rating?.toInt()) {
                    1 -> list_rate_1?.add(it)
                    2 -> list_rate_2?.add(it)
                    3 -> list_rate_3?.add(it)
                    4 -> list_rate_4?.add(it)
                    5 -> list_rate_5?.add(it)
                }
            }

            with(binding) {
                textViewRating.text = String.format("%.1f", data?.rating?.rating?.toFloat())
                ratingAll.rating = data?.rating?.rating?.toFloat() ?: 0f
                textUlasanAll.text = "${count_rate_all} Ulasan"

                textCountRate1.text = count_rate_1.toString()
                textCountRate2.text = count_rate_2.toString()
                textCountRate3.text = count_rate_3.toString()
                textCountRate4.text = count_rate_4.toString()
                textCountRate5.text = count_rate_5.toString()

                adapter = UlasanAdapter(applicationContext, data?.detail)
                adapter.setCurrentViewType(ITEM_ULASAN)
                listUlasan.adapter = adapter

            }

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }

    }

    private fun initView() {

        list_rate_1 = ArrayList()
        list_rate_2 = ArrayList()
        list_rate_3 = ArrayList()
        list_rate_4 = ArrayList()
        list_rate_5 = ArrayList()

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            viewModel.performanceDriver(sessionManager.id)

            rb5Star.setOnClickListener {
                adapter = UlasanAdapter(applicationContext, list_rate_5)
                adapter.setCurrentViewType(ITEM_ULASAN)
                adapter.notifyDataSetChanged()
                listUlasan.adapter = adapter
            }

            rb4Star.setOnClickListener {
                adapter = UlasanAdapter(applicationContext, list_rate_4)
                adapter.setCurrentViewType(ITEM_ULASAN)
                adapter.notifyDataSetChanged()
                listUlasan.adapter = adapter
            }

            rb3Star.setOnClickListener {
                adapter = UlasanAdapter(applicationContext, list_rate_3)
                adapter.setCurrentViewType(ITEM_ULASAN)
                adapter.notifyDataSetChanged()
                listUlasan.adapter = adapter
            }

            rb2Star.setOnClickListener {
                adapter = UlasanAdapter(applicationContext, list_rate_2)
                adapter.setCurrentViewType(ITEM_ULASAN)
                adapter.notifyDataSetChanged()
                listUlasan.adapter = adapter
            }

            rb1Star.setOnClickListener {
                adapter = UlasanAdapter(applicationContext, list_rate_1)
                adapter.setCurrentViewType(ITEM_ULASAN)
                adapter.notifyDataSetChanged()
                listUlasan.adapter = adapter
            }
        }
    }
}