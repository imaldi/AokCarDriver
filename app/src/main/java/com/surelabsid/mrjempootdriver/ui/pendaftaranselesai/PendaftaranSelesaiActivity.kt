package com.surelabsid.mrjempootdriver.ui.pendaftaranselesai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranSelesaiBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginNoTeleponActivity
import com.surelabsid.mrjempootdriver.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendaftaranSelesaiActivity : BaseActivity() {

    private lateinit var binding: ActivityPendaftaranSelesaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranSelesaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()


    }

    private fun initView() {
        with(binding) {
            buttonMasuk.setOnClickListener {
                sessionManager.flag_create_password = true
                openActivity(BuatPasswordActivity::class.java)
//                sessionManager.flag_create_password = true
//                openActivity(LoginNoTeleponActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sessionManager.flag_create_password = false
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.flag_create_password = false
    }
}