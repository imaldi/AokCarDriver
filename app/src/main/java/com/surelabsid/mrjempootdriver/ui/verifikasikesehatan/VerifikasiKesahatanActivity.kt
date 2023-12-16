package com.surelabsid.mrjempootdriver.ui.verifikasikesehatan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityVerifikasiKesahatanBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseHealthDriver
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest.RequestHealthVerification
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.viewmodel.VerifikasiKesehatanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.DOCUMENT_BEBAS_COVID_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.DOCUMENT_SWAB_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.DOCUMENT_VAKSIN_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_ITEM_HEALTH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifikasiKesahatanActivity : BaseActivity() {

    private lateinit var binding: ActivityVerifikasiKesahatanBinding

    private lateinit var viewModel: VerifikasiKesehatanViewModel

    private var document_vaksin: String = ""
    private var document_swab: String = ""
    private var document_bebas_covid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiKesahatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(VerifikasiKesehatanViewModel::class.java)

        binding.toolbar.title.text = "Verifikasi Kesehatan Partner"
        binding.toolbar.backButton.setOnClickListener {
            onBackPressed()
        }

        initPermissionCamera()

        initView()

        attachObserve()

    }

    private fun attachObserve() {
        with(viewModel) {
            health_verification.observe(
                this@VerifikasiKesahatanActivity,
                { responseHealthVerification(it) })
            health_driver.observe(this@VerifikasiKesahatanActivity, { showHealtDriver(it) })
            loading.observe(this@VerifikasiKesahatanActivity, { isLoading(it) })
            error.observe(
                this@VerifikasiKesahatanActivity,
                { showError(this@VerifikasiKesahatanActivity, it) })
        }
    }

    private fun showHealtDriver(it: ResponseHealthDriver?) {
        if (it?.code == "200") {

            val item = it.data

            with(binding) {

//                kirim.visibility = View.INVISIBLE

                if (item?.vaccine != null) {
                    Glide.with(baseContext).load(BASE_URL_ITEM_HEALTH + item.vaccine).into(imageViewVaksin)
                    checkboxVaksin.isChecked = true
                    checkboxVaksin.isEnabled = false
                    buttonUploadVaksin.isEnabled = false
                }

                if (item?.swab != null) {
                    Glide.with(baseContext).load(BASE_URL_ITEM_HEALTH + item.swab).into(imageViewSwab)
                    checkboxSwab.isChecked = true
                    checkboxSwab.isEnabled = false
                    buttonUploadSwab.isEnabled = false
                }

                if (item?.freeCovid != null) {
                    Glide.with(baseContext).load(BASE_URL_ITEM_HEALTH + item.freeCovid).into(imageViewSuratKeterangan)
                    checkboxBebasCovid.isChecked = true
                    checkboxBebasCovid.isEnabled = false
                    buttonUploadBebasCovid.isEnabled = false
                }
            }
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                kirim.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                kirim.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun responseHealthVerification(it: ResponseReqTopup?) {
        if (it?.code == "200") {

            finish()

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {

        viewModel.healthDriver(sessionManager.id)

        with(binding) {
            buttonUploadVaksin.setOnClickListener {
                val intent =
                    Intent(this@VerifikasiKesahatanActivity, UploadBuktiVaksinActivity::class.java)
                startActivityForResult(intent, DOCUMENT_VAKSIN_RESULT)
            }

            buttonUploadSwab.setOnClickListener {
                val intent =
                    Intent(this@VerifikasiKesahatanActivity, UploadSwabActivity::class.java)
                startActivityForResult(intent, DOCUMENT_SWAB_RESULT)
            }

            buttonUploadBebasCovid.setOnClickListener {
                val intent =
                    Intent(this@VerifikasiKesahatanActivity, UploadSuratActivity::class.java)
                startActivityForResult(intent, DOCUMENT_BEBAS_COVID_RESULT)
            }

            kirim.setOnClickListener {
                if (document_vaksin == "") {
                    binding.root.showSnackbar(
                        getString(R.string.document_vaksin_empty),
                        Snackbar.LENGTH_SHORT
                    )
                } else if (document_swab == "") {
                    binding.root.showSnackbar(
                        getString(R.string.document_swab_empty),
                        Snackbar.LENGTH_SHORT
                    )
                } else if (document_bebas_covid == "") {
                    binding.root.showSnackbar(
                        getString(R.string.document_bebas_covid_empty),
                        Snackbar.LENGTH_SHORT
                    )
                } else {
                    val param = RequestHealthVerification(
                        sessionManager.id,
                        encodeBase64(document_vaksin),
                        encodeBase64(document_swab),
                        encodeBase64(document_bebas_covid)
                    )
                    viewModel.healthVerification(param)
                }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DOCUMENT_VAKSIN_RESULT) {
            document_vaksin = data?.getStringExtra("document_vaksin").toString()
        } else if (requestCode == DOCUMENT_SWAB_RESULT) {
            document_swab = data?.getStringExtra("document_swab").toString()
        } else if (requestCode == DOCUMENT_BEBAS_COVID_RESULT) {
            document_bebas_covid = data?.getStringExtra("document_bebas_covid").toString()
        }

        checkDocument()
    }

    private fun checkDocument() {
        if (document_vaksin != "") {
            binding.checkboxVaksin.isChecked = true
            binding.imageViewVaksin.setImageURI(Uri.parse(document_vaksin))
        } else {
            binding.checkboxVaksin.isChecked = false
        }

        if (document_swab != "") {
            binding.checkboxSwab.isChecked = true
            binding.imageViewSwab.setImageURI(Uri.parse(document_swab))
        } else {
            binding.checkboxSwab.isChecked = false
        }

        if (document_bebas_covid != "") {
            binding.checkboxBebasCovid.isChecked = true
            binding.imageViewSuratKeterangan.setImageURI(Uri.parse(document_bebas_covid))
        } else {
            binding.checkboxBebasCovid.isChecked = false
        }
    }

    override fun onResume() {
        super.onResume()
        checkDocument()
    }
}