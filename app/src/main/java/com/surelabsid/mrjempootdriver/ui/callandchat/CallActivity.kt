package com.surelabsid.mrjempootdriver.ui.callandchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surelabsid.mrjempootdriver.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {

    lateinit var binding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}