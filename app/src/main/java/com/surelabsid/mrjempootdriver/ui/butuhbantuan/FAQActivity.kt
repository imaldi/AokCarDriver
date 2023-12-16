package com.surelabsid.mrjempootdriver.ui.butuhbantuan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.surelabsid.mrjempootdriver.databinding.ActivityFaqactivityBinding
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.FAQ
import com.surelabsid.mrjempootdriver.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class FAQActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: ActivityFaqactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var appSettings = AppSettings()
        if(sessionManager.app_settings != ""){
            appSettings = Json.decodeFromString<AppSettings>(sessionManager.app_settings)

            binding.webview.apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(FAQ)
            }

        }

    }
}