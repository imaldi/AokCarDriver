package com.surelabsid.mrjempootdriver.ui.peforma

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.surelabsid.mrjempootdriver.databinding.FragmentPeformaBinding
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.ulasan.adapter.UlasanAdapter
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.DetailItem
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import com.surelabsid.mrjempootdriver.ui.ulasan.viewmodel.UlasanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class PeformanceFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: FragmentPeformaBinding

    private lateinit var viewModelUlasan: UlasanViewModel

    // variable for our bar data.
    var barData: BarData? = null

    // variable for our bar data set.
    var barDataSet: BarDataSet? = null

    // array list for storing entries.
    var barEntriesArrayList: ArrayList<BarEntry>? = null

    private var total_order: ArrayList<String>? = null

    private var list_rate_1: MutableList<DetailItem>? = null
    private var list_rate_2: MutableList<DetailItem>? = null
    private var list_rate_3: MutableList<DetailItem>? = null
    private var list_rate_4: MutableList<DetailItem>? = null
    private var list_rate_5: MutableList<DetailItem>? = null

    private var month_1: Int = 0
    private var month_2: Int = 0
    private var month_3: Int = 0
    private var month_4: Int = 0
    private var month_5: Int = 0
    private var month_6: Int = 0
    private var month_7: Int = 0
    private var month_8: Int = 0
    private var month_9: Int = 0
    private var month_10: Int = 0
    private var month_11: Int = 0
    private var month_12: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeformaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelUlasan = ViewModelProvider(this).get(UlasanViewModel::class.java)

        initView()
        attachObserve()

    }

    private fun attachObserve() {
        with(viewModelUlasan) {
            performance_driver.observe(viewLifecycleOwner, { responsePerformanceDriver(it) })
        }
    }

    private fun responsePerformanceDriver(it: ResponsePerformanceDriver?) {
        if (it?.code == "200") {
            
            val data = it.data
            val detail = data?.detail

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

                textCountRate1.text = count_rate_1.toString()
                textCountRate2.text = count_rate_2.toString()
                textCountRate3.text = count_rate_3.toString()
                textCountRate4.text = count_rate_4.toString()
                textCountRate5.text = count_rate_5.toString()

            }

            detail?.forEach {
                Log.d( "response: ", "${formatGetMonth(it?.updateAt)}")
                val getMonth = formatGetMonth(it?.updateAt)
                when (getMonth) {
                    "01" -> {
                        month_1 += 1
                    }
                    "02" -> {
                        month_2 += 1
                    }
                    "03" -> {
                        month_3 += 1
                    }
                    "04" -> {
                        month_4 += 1
                    }
                    "05" -> {
                        month_5 += 1
                    }
                    "06" -> {
                        month_6 += 1
                    }
                    "07" -> {
                        month_7 += 1
                    }
                    "08" -> {
                        month_8 += 1
                    }
                    "09" -> {
                        month_9 += 1
                    }
                    "10" -> {
                        month_10 += 1
                    }
                    "11" -> {
                        month_11 += 1
                    }
                    "12" -> {
                        month_12 += 1
                    }
                    else -> {
                        Log.d( "response: ", "Not bulan ${getMonth}",)
                    }
                }
            }

            initChart()
            initChart()

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

        total_order = ArrayList()
        viewModelUlasan.performanceDriver(sessionManager.id)
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    private fun initChart() {
        with(binding) {
            // initializing variable for bar chart.
            // calling method to get bar entries.
            getBarEntries()

            // creating a new bar data set.
            barDataSet = BarDataSet(barEntriesArrayList, "")

            // creating a new bar data and
            // passing our bar data set.

            barData = BarData(barDataSet)

            // below line is to set data
            // to our bar chart.

            chart.data = barData
            chart.setScaleEnabled(false)

            // adding color to our bar data set.

            barDataSet?.setColors(ContextCompat.getColor(binding.root.context, R.color.primary_color))

            // setting text color.

            barDataSet?.valueTextColor = Color.BLACK
            barDataSet?.setDrawValues(false)
            // setting text size
            barDataSet?.valueTextSize = 16f
            chart.description.isEnabled = false
            chart.invalidate()

            val xAxis = chart.xAxis
            xAxis.isEnabled = true
            xAxis.axisLineColor = Color.RED
            xAxis.valueFormatter = MonthValueFormatter()
            xAxis.textColor = Color.TRANSPARENT
            xAxis.gridColor = Color.TRANSPARENT
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.axisLineColor = ContextCompat.getColor(binding.root.context, R.color.primary_color)

            val yAxisRight = chart.axisRight
            yAxisRight.isEnabled = false

            val yAxisLeft = chart.axisLeft
            yAxisLeft.axisLineColor = ContextCompat.getColor(binding.root.context, R.color.primary_color)
            yAxisLeft.gridColor = Color.TRANSPARENT
            yAxisLeft.setStartAtZero(true)



        }
    }

    private fun getBarEntries() {
        // creating a new array list
        barEntriesArrayList = ArrayList()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList!!.add(BarEntry(0f, month_1.toFloat()))
        barEntriesArrayList!!.add(BarEntry(1f, month_2.toFloat()))
        barEntriesArrayList!!.add(BarEntry(2f, month_3.toFloat()))
        barEntriesArrayList!!.add(BarEntry(3f, month_4.toFloat()))
        barEntriesArrayList!!.add(BarEntry(4f, month_5.toFloat()))
        barEntriesArrayList!!.add(BarEntry(5f, month_6.toFloat()))
        barEntriesArrayList!!.add(BarEntry(6f, month_7.toFloat()))
        barEntriesArrayList!!.add(BarEntry(7f, month_8.toFloat()))
        barEntriesArrayList!!.add(BarEntry(8f, month_9.toFloat()))
        barEntriesArrayList!!.add(BarEntry(9f, month_10.toFloat()))
        barEntriesArrayList!!.add(BarEntry(10f, month_11.toFloat()))
        barEntriesArrayList!!.add(BarEntry(11f, month_12.toFloat()))

//        barEntriesArrayList!!.add(BarEntry(0f, 10f))
//        barEntriesArrayList!!.add(BarEntry(1f, 20f))
//        barEntriesArrayList!!.add(BarEntry(2f, 30f))
//        barEntriesArrayList!!.add(BarEntry(3f, 40f))
//        barEntriesArrayList!!.add(BarEntry(4f, 50f))
//        barEntriesArrayList!!.add(BarEntry(5f, 60f))
//        barEntriesArrayList!!.add(BarEntry(6f, 70f))
//        barEntriesArrayList!!.add(BarEntry(7f, 80f))
//        barEntriesArrayList!!.add(BarEntry(8f, 90f))
//        barEntriesArrayList!!.add(BarEntry(9f, 100f))
//        barEntriesArrayList!!.add(BarEntry(10f, 110f))
//        barEntriesArrayList!!.add(BarEntry(11f, 120f))

    }

}

class MonthValueFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return when (value) {
            in 0.0f..0.9f  -> "January"
            in 1.0f..1.9f -> "February"
            in 2.0f..2.9f -> "March"
            in 3.0f..3.9f -> "April"
            in 4.0f..4.9f -> "May"
            in 5.0f..5.9f -> "June"
            in 6.0f..6.9f -> "July"
            in 7.0f..7.9f -> "August"
            in 8.0f..8.9f -> "September"
            in 9.0f..9.9f -> "October"
            in 10.0f..10.9f -> "November"
            in 11.0f..11.9f -> "December"
            else -> throw IllegalArgumentException("$value is not a valid month")
        }
    }
}