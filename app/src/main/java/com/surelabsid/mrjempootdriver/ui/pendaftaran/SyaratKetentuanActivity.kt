package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivitySyaratKetentuanBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.utils.encodeBase64
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class SyaratKetentuanActivity : BaseActivity() {

    lateinit var binding: ActivitySyaratKetentuanBinding

    lateinit var viewModel: PendaftaranViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySyaratKetentuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            register_driver.observe(this@SyaratKetentuanActivity, { responseRegister(it) })
            loading.observe(this@SyaratKetentuanActivity, { isLoading(it) })
            error.observe(
                this@SyaratKetentuanActivity,
                { showError(this@SyaratKetentuanActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                butonKirim.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                butonKirim.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun responseRegister(it: ResponseRegisterDriver?) {

        if (it?.code == "200") {
            val intent = Intent(this, PendaftaranVerifikasiActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_LONG)
        }
    }

    private fun initView() {
        with(binding) {

            var appSettings = AppSettings()
            if(sessionManager.app_settings != ""){
                appSettings = Json.decodeFromString<AppSettings>(sessionManager.app_settings)

                webview.apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadData(appSettings.appSyarat.toString(), "text/html", "UTF-8")
                }

            }

            if (intent.getBooleanExtra("from_profile", false)) {
                appCompatCheckBox.visibility = View.GONE
                butonKirim.visibility = View.GONE
            } else {
                appCompatCheckBox.visibility = View.VISIBLE
                butonKirim.visibility = View.VISIBLE
            }

            butonKirim.visibility = View.INVISIBLE

            imageButtonBack.setOnClickListener {
                this@SyaratKetentuanActivity.onBackPressed()
            }

            appCompatCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    butonKirim.visibility = View.VISIBLE
                } else {
                    butonKirim.visibility = View.INVISIBLE
                }
            }

            binding.butonKirim.setOnClickListener {
                with(sessionManager) {
                    val param = RequestRegisterDriver(
                        driver_name = full_name,
                        gender = gender,
                        email = email,
                        dob = birth_date,
                        job = job,
                        driver_address = "$address, $address_detail",
                        countrycode = dial_code,
                        phone = username,
                        phone_number = dial_code.substring(1) + username,
                        driver_license_id = sim_document_id,
                        user_nationid = ktp_id,
                        family_relationship_id = emergency_relation_id,
                        contact_number = emergency_dial_code.substring(1) + emergency_phone_number,
                        contact_name = emergency_name,
                        brand = vehicle_brand,
                        type = vehicle_type,
                        vehicle_registration_number = vehicle_plate,
                        color = vehicle_color,
                        photo = if(photo_profile != "") encodeBase64(photo_profile) else "",
//                        photo = "default",
                        idcard_images = if(ktp_document != "") encodeBase64(ktp_document) else "",
//                        idcard_images = "default",
                        driver_license_images = if(sim_document != "") encodeBase64(sim_document) else "",
//                        driver_license_images = "default",
                        driver_stnk = if(stnk_document != "") encodeBase64(stnk_document) else "",
                        driver_skck = if(skck_document != "") encodeBase64(skck_document) else "",
                        token = sessionManager.token,
                        checked = "false"
                    )

                    viewModel.registerDriver(param)


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