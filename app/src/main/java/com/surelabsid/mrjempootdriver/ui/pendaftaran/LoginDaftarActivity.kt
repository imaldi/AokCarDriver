package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.surelabsid.mrjempootdriver.databinding.ActivityLoginDaftarBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseAppSettings
import com.surelabsid.mrjempootdriver.ui.beranda.viewmodel.HomeViewModel
import com.surelabsid.mrjempootdriver.ui.login.LoginActivity
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.ACCESS_LOCATION
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginDaftarActivity : BaseActivity() {

    lateinit var binding: ActivityLoginDaftarBinding

    lateinit var viewModelHome: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelHome = ViewModelProvider(this).get(HomeViewModel::class.java)

        locationPermission()

        getToken()

        viewModelHome.appSettings()
        viewModelHome.app_settings.observe(this, {
            responseAppSettings(it)
        })

        binding.buttonMasuk.setOnClickListener {
            openActivity(LoginActivity::class.java)
        }
        binding.buttonDaftar.setOnClickListener {
            openActivity(LoginNoTeleponActivity::class.java)
        }
        binding.textDaftar.setOnClickListener {
            openActivity(LoginNoTeleponActivity::class.java)
        }
    }

    private fun responseAppSettings(it: ResponseAppSettings?) {
        if (it?.code == 200) {
            sessionManager.app_settings = Json.encodeToString(it.appSettings)
            sessionManager.app_id_verihubs = it.appSettings?.verihubsAppId.toString()
            sessionManager.api_key_verihubs = it.appSettings?.verihubsKey.toString()
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun locationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                locationUpdater?.start()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), ACCESS_LOCATION
                )
            }
        } else {
//            locationUpdater?.start()
        }

    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
                task ->
            if (!task.isSuccessful) {
                Log.w( "getToken: ", "Fetching FCM registration token failed", task.exception )
                return@OnCompleteListener
            }

            //Get new FCM registration token
            val token = task.result

            //Save token
            sessionManager.token = token

        })
    }

}