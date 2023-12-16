package com.surelabsid.mrjempootdriver.ui.profil

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.surelabsid.mrjempootdriver.databinding.ActivityKebijakanPrivacyBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class KebijakanPrivacyActivity : BaseActivity() {

    private lateinit var binding: ActivityKebijakanPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKebijakanPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            var appSettings = AppSettings()
            if(sessionManager.app_settings != ""){
                appSettings = Json.decodeFromString<AppSettings>(sessionManager.app_settings)

                webview.apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadData(appSettings.appPrivacyPolicy.toString(), "text/html", "UTF-8")
                }

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (binding.webview != null) {
            binding.webview.destroy()
        }
    }

}