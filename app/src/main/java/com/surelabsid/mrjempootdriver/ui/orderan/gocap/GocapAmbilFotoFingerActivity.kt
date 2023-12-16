package com.surelabsid.mrjempootdriver.ui.orderan.gocap

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityGocapAmbilFotoFingerBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestSendBuktiTerimaPaket
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.viewmodel.OrderanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.AMBIL_FOTO_FINGER_RESULT
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.transaction_status.PROCESS
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_RECEIVE_PACKET_PHOTO
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class GocapAmbilFotoFingerActivity : BaseActivity() {

    private lateinit var binding: ActivityGocapAmbilFotoFingerBinding

    private lateinit var viewModel: OrderanViewModel

    private var image_path: String = ""
    private var image_path2: String = ""

    private var mime_type: String? = null
    private var mime_type2: String? = null

    private var item_orderan: DataItem? = null
    private var ambil_foto_finger: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGocapAmbilFotoFingerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(OrderanViewModel::class.java)

        item_orderan = intent.getSerializableExtra("item_orderan") as DataItem
        ambil_foto_finger = intent.getBooleanExtra("ambil_foto_finger", false)

        initPermissionCamera()
        initView()

        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            send_bukti_foto_jemput_paket.observe(
                this@GocapAmbilFotoFingerActivity,
                { responseBuktiFotoJemputPaket(it) })
            status_transaction_ambil_paket.observe(
                this@GocapAmbilFotoFingerActivity,
                { responseStatusTransactionAmbilPaket(it) })
            status_transaction_ambil_foto_finger.observe(
                this@GocapAmbilFotoFingerActivity,
                { responseStatusTransactionAmbilFotoFinger(it) })
            update_button_name_orderan_local.observe(
                this@GocapAmbilFotoFingerActivity,
                { responseUpdateButtonNameOrderanLocal(it) })
            error.observe(
                this@GocapAmbilFotoFingerActivity,
                { showError(this@GocapAmbilFotoFingerActivity, it) })
            loading.observe(this@GocapAmbilFotoFingerActivity, { isLoading(it) })
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            binding.button8.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.button8.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun responseUpdateButtonNameOrderanLocal(it: Boolean?) {
        if (it == true) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("is_success", true)
            setResult(AMBIL_FOTO_FINGER_RESULT, intent)
            finish()
        }
    }


    private fun responseStatusTransactionAmbilPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.paket_sedang_dalam_perjalanan)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseStatusTransactionAmbilFotoFinger(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            sessionManager.temp_transaction_id = it.data?.get(0)?.id.toString()

            viewModel.updateButtonNameOrderanLocal(
                sessionManager.temp_transaction_id.toInt(),
                getString(R.string.bayar_mr_jempoot)
            )

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseBuktiFotoJemputPaket(it: ResponseStatusTransaction?) {
        if (it?.code == "200") {

            if (!ambil_foto_finger) {
                val keterangan = getString(R.string.udah_jemput_paket)
                val param = RequestStatusTransaction(
                    sessionManager.id,
                    it.data?.get(0)?.id.toString(),
                    PROCESS.toString(),
                    keterangan
                )
                viewModel.transactionStatusAmbilPaket(param)
            } else {
                val keterangan = getString(R.string.ambil_foto_dan_finger)
                val param = RequestStatusTransaction(
                    sessionManager.id,
                    it.data?.get(0)?.id.toString(),
                    PROCESS.toString(),
                    keterangan
                )
                viewModel.transactionStatusAmbilFotoFinger(param)
            }


        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }

            if (!ambil_foto_finger) {
                imageView13.isEnabled = true
                imageView14.isEnabled = false
            } else {
                imageView13.isEnabled = false
                imageView14.isEnabled = true
                if (item_orderan?.receivePacket != null) {
                    Glide.with(this@GocapAmbilFotoFingerActivity)
                        .load(BASE_URL_RECEIVE_PACKET_PHOTO + item_orderan?.receivePacket?.imageBefore)
                        .into(imageView13)
                }
            }

            imageView13.setOnClickListener {
                openCamera()
            }

            imageView14.setOnClickListener {
                openCamera(2)
            }

            button8.setOnClickListener {

                var param = RequestSendBuktiTerimaPaket("", "", "", "")

                if (!ambil_foto_finger) {
                    param = RequestSendBuktiTerimaPaket(
                        sessionManager.id,
                        item_orderan?.id ?: "",
                        encodeBase64(image_path),
                        ""
                    )
                } else {
                    param = RequestSendBuktiTerimaPaket(
                        sessionManager.id,
                        item_orderan?.id ?: "",
                        "",
                        encodeBase64(image_path2)
                    )
                }


                Log.d("TAG", "initView: $param")

                viewModel.sendBuktiFotoJemputPaket(param)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.Companion.code_request.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
        } else {
            initCamera2(data)
        }
    }

    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "GocapFotoJemput$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

            binding.imageView13.setImageBitmap(BitmapFactory.decodeFile(image_path))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private fun initCamera2(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "GocapFotoTerima$random"

            image_path2 = persistImage(image as Bitmap, name_file)

            mime_type2 = getMimeTypeFile(Uri.parse(image_path2))

            Log.d("TAG", "initCamera: MimeType : $mime_type2")

            binding.imageView14.setImageBitmap(BitmapFactory.decodeFile(image_path2))

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }


}