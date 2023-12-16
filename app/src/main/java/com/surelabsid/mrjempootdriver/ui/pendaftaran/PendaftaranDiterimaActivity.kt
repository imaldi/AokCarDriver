package com.surelabsid.mrjempootdriver.ui.pendaftaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranDiterimaBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendaftaranDiterimaActivity : BaseActivity() {

    lateinit var binding: ActivityPendaftaranDiterimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranDiterimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        with(binding) {
            buttonKeluar.setOnClickListener {
                onBackPressed()
            }
        }
    }
}