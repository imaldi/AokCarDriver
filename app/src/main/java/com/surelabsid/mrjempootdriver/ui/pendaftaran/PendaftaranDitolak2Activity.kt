package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranDitolak2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendaftaranDitolak2Activity : AppCompatActivity() {

    lateinit var binding: ActivityPendaftaranDitolak2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranDitolak2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        with(binding) {
            textView2.text = intent.getStringExtra("reason_rejected").toString()
        }
    }
}