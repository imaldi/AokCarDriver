package com.surelabsid.mrjempootdriver.ui.topup

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityTopUpSaldoBinding
import com.surelabsid.mrjempootdriver.databinding.DialogTopupBerhasilBinding
import com.surelabsid.mrjempootdriver.utils.Constant

class TopUpSaldoActivity : AppCompatActivity() {

    lateinit var binding: ActivityTopUpSaldoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpSaldoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            linear10.setOnClickListener {
                editTextNominal.setText("10000")
            }
            linear50.setOnClickListener {
                editTextNominal.setText("50000")
            }
            linear100.setOnClickListener {
                editTextNominal.setText("100000")
            }
            linear200.setOnClickListener {
                editTextNominal.setText("200000")
            }

            buttonSelanjutnya.setOnClickListener {
                if (TextUtils.isEmpty(binding.editTextNominal.text.toString())) {
                    binding.editTextNominal.error = getString(R.string.nominal_empty)
                } else {
                    val intent = Intent(this@TopUpSaldoActivity, TopUpKonfirmasiActivity::class.java)
                    intent.putExtra("amount", editTextNominal.text.toString())
                    startActivity(intent)
                    finish()
                }
            }

        }
    }
}