package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityIdentifikasiWajah5Binding
import com.surelabsid.mrjempootdriver.utils.moveActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentifikasiWajah5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentifikasiWajah5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentifikasiWajah5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSelanjutnya.setOnClickListener {
            moveActivity(InformasiMotorActivity::class.java)
        }
    }
}