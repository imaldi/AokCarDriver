package com.surelabsid.mrjempootdriver.ui.orderan.gopek

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGopekAmbilFotoBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestSendStruk
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.BAYAR_MR_JEMPOOT_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.KIRIM_STRUK_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.PROCESS
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class GopekAmbilFotoActivity : BaseActivity() {

    private lateinit var binding: ActivityGopekAmbilFotoBinding

    private lateinit var viewModel: OrderanViewModel

    private var image_path: String? = null

    private var mime_type: String? = null

    private var item_orderan: DataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGopekAmbilFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)

        item_orderan = intent.getSerializableExtra("item_orderan") as DataItem

        initPermissionCamera()
        initView()

        attachObseve()

    }

    private fun attachObseve() {
        with(viewModel) {
            send_struk.observe(this@GopekAmbilFotoActivity, { responseSendStruk(it) })
            status_transaction_kirim_struk_customer.observe(
                this@GopekAmbilFotoActivity,
                { responseStatusTransactionKirimStrukCustomer(it) })
            update_button_name_orderan_local.observe(this@GopekAmbilFotoActivity, { responseUpdateButtonNameOrderanLocal(it) })
            loading.observe(this@GopekAmbilFotoActivity, { isLoading(it) })
            error.observe(this@GopekAmbilFotoActivity, { showError(this@GopekAmbilFotoActivity, it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonKirim.visibility = View.INVISIBLE
            binding.buttonRetake.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.buttonKirim.visibility = View.VISIBLE
            binding.buttonRetake.visibility = View.VISIBLE
        }
    }

    private fun responseUpdateButtonNameOrderanLocal(it: Boolean?) {
        if (it == true) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("is_success", true)
            setResult(KIRIM_STRUK_RESULT, intent)
            finish()
        }
    }


    private fun responseSendStruk(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {
            val item = it.data?.get(0)

            val keterangan = getString(R.string.kirim_struk_ke_customer)
            val param = RequestStatusTransaction(
                sessionManager.id,
                item?.id.toString(),
                PROCESS.toString(),
                keterangan
            )
            viewModel.transactionStatusKirimStrukCustomer(param)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionKirimStrukCustomer(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.pesanan_sedang_diantar)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }


    private fun initView() {

        openCamera()

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
            buttonRetake.setOnClickListener {
                openCamera()
            }

            buttonKirim.setOnClickListener {
                if (image_path == null) {
                    binding.root.showSnackbar(getString(R.string.struk_empty), Snackbar.LENGTH_SHORT)
                } else {
                    val param = RequestSendStruk(sessionManager.id, item_orderan?.id ?: "", encodeBase64(image_path))
                    viewModel.sendStruk(param)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.Companion.code_request.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
        }
    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "GopekFoto$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageView12.setImageBitmap(BitmapFactory.decodeFile(image_path))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

}