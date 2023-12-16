package com.surelabsid.mrjempootdriver.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginDaftarActivity
import com.surelabsid.mrjempootdriver.ui.onboarding.OnBoardingActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.IdentifikasiWajah1Activity
import com.surelabsid.mrjempootdriver.utils.moveActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            if (sessionManager.on_boarding){

                if (sessionManager.is_login) {
                    moveActivity(MainActivity::class.java)
                } else {
                    moveActivity(LoginDaftarActivity::class.java)
                }

            } else {
                moveActivity(OnBoardingActivity::class.java)
            }
        }, 3000)
    }
}