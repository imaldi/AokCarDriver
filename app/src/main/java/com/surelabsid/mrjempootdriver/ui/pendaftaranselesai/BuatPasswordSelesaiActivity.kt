package com.surelabsid.mrjempootdriver.ui.pendaftaranselesai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityBuatPasswordSelesaiBinding
import com.surelabsid.mrjempootdriver.ui.login.LoginActivity

class BuatPasswordSelesaiActivity : AppCompatActivity() {

    lateinit var binding: ActivityBuatPasswordSelesaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuatPasswordSelesaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding) {
            buttonMasuk.setOnClickListener {
                val intent = Intent(this@BuatPasswordSelesaiActivity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }

                startActivity(intent)
            }
        }
    }
}