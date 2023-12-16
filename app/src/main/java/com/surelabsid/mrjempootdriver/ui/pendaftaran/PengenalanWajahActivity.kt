package com.surelabsid.mrjempootdriver.ui.pendaftaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.mrjempootdriver.databinding.ActivityPengenalanWajahBinding
import com.surelabsid.mrjempootdriver.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PengenalanWajahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengenalanWajahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengenalanWajahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button9.setOnClickListener {
            openActivity(IdentifikasiWajah1Activity::class.java)
        }


    }
}