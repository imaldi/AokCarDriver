package com.surelabsid.mrjempootdriver.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.onBoardingContainer, OnBoarding1Fragment())
            .commit()
    }
}