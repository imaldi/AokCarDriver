package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranVerifikasiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendaftaranVerifikasiActivity : AppCompatActivity() {

    lateinit var binding: ActivityPendaftaranVerifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        with(binding) {
            buttonKeluar.setOnClickListener {
                val intent = Intent(this@PendaftaranVerifikasiActivity, LoginDaftarActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
                finish()
            }
        }
    }
}