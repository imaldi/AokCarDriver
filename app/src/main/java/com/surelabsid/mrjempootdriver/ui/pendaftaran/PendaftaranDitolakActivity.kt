package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranDitolakBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendaftaranDitolakActivity : BaseActivity() {

    lateinit var binding: ActivityPendaftaranDitolakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranDitolakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding) {
            buttonKenapaDitolak.setOnClickListener {
                Intent(this@PendaftaranDitolakActivity, PendaftaranDitolak2Activity::class.java).apply {
                    putExtra("reason_rejected", intent.getStringExtra("reason_rejected").toString())
                    startActivity(this)
                }
            }

            buttonKeluar.setOnClickListener {
                finish()
            }
        }
    }
}