package com.surelabsid.mrjempootdriver.ui.pendaftaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.mrjempootdriver.databinding.ActivityJenisTransportBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.utils.moveActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JenisTransportActivity : BaseActivity() {

    private lateinit var binding: ActivityJenisTransportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
            buttonLanjut.setOnClickListener {
                moveActivity(PendaftaranActivity::class.java)
            }

            radioButtonMotor.setOnClickListener {
                sessionManager.vehicle_kind = radioButtonMotor.text.toString()
                sessionManager.job = "7"
                radioButtonNewSelis.isChecked = false
            }

            radioButtonNewSelis.setOnClickListener {
                sessionManager.vehicle_kind = radioButtonNewSelis.text.toString()
                sessionManager.job = "8"
                radioButtonMotor.isChecked = false
            }
        }
    }
}