package com.surelabsid.mrjempootdriver.ui.butuhbantuan

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityChatCsactivityBinding
import com.surelabsid.mrjempootdriver.service.modelresponse.ResponseNotification
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.adapter.ChatCSAdapter
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseGetChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.viewmodel.ButuhBantuanViewModel
import com.surelabsid.mrjempootdriver.ui.callandchat.ChatPhotoFragment
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.random.Random

@AndroidEntryPoint
class ChatCSActivity : BaseActivity() {

    lateinit var binding: ActivityChatCsactivityBinding

    lateinit var viewModel: ButuhBantuanViewModel

    private var is_attach = false

    private var image_path: String? = null

    private var mime_type: String? = null

    private var chatPhotoFragment = ChatPhotoFragment()

    private var data_notif: ResponseNotification? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatCsactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ButuhBantuanViewModel::class.java)

        initPermissionStorageAndCamera()

        initView()
        attachObserve()

        loadChat()

    }

    private fun loadChat() {
        if (sessionManager.id_message_cs != "" && sessionManager.id_message_cs != null) {
            viewModel.getChatCS(sessionManager.id_message_cs)
        }
    }

    private fun attachObserve() {
        with(viewModel) {
            send_chat_cs.observe(this@ChatCSActivity, { responseSendChatCS(it) })
            get_chat_cs.observe(this@ChatCSActivity, { responseGetChatCS(it) })
            error.observe(this@ChatCSActivity, { showError(this@ChatCSActivity, it) })
            loading_send_chat.observe(this@ChatCSActivity, { loadingSendChat(it) })

        }
    }

    private fun loadingSendChat(it: Boolean?) {
        if (it == true) {
            binding.progressBarKirim.show()
            binding.btnKirim.hide()
        } else {
            binding.progressBarKirim.hide()
            binding.btnKirim.show()
        }
    }

    private fun responseGetChatCS(it: ResponseGetChatCS?) {
        if (it?.code == "200") {
            binding.progressBarChat.hide()
            binding.recyclerViewChat.adapter = ChatCSAdapter(sessionManager.id, it.data?.detailChat, object : ChatCSAdapter.OnClick {
                override fun onImageClick(urlPhoto: String) {

                    val bundle = Bundle()
                    bundle.putString("photo_url", urlPhoto)
                    bundle.putBoolean("is_firebase", false)
                    chatPhotoFragment.arguments = bundle
                    chatPhotoFragment.show(supportFragmentManager, "")

                }

            })

//            binding.recyclerViewChat.scrollToPosition(it.data?.detailChat?.size ?: 0 - 1)
            binding.recyclerViewChat.smoothScrollToPosition(it.data?.detailChat?.size ?: 0 - 1)


        }
    }

    private fun responseSendChatCS(it: ResponseSendChatCS?) {
        if (it?.code == "200") {
            showToast(this, it.message)
            binding.textMessage.setText("")
            viewModel.getChatCS(sessionManager.id_message_cs)
        } else {
            binding.root.showSnackbar("${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                finish()
            }

            btnKirim.setOnClickListener {

                if (!TextUtils.isEmpty(textMessage.text.toString())) {
                    val param = RequestSendChatCS(
                        sessionManager.id_message_cs,
                        sessionManager.id,
                        textMessage.text.toString()
                    )
                    viewModel.sendChatCS(param)
                }

            }

            btnAttach.setOnClickListener {
                is_attach = is_attach == false
                setDialog()
            }

            binding.btnCamera.setOnClickListener {
                openCamera()
            }

            binding.btnGallery.setOnClickListener {
                openGallery()
            }

        }
    }

    private fun setDialog() {
        if (is_attach == false) {
            binding.layoutAttach.visibility = View.GONE
        } else {
            binding.layoutAttach.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == Constant.Companion.code_request.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
//            binding.progressBarKirim.visibility = View.VISIBLE
//            binding.btnKirim.visibility = View.GONE
        } else if (requestCode == Constant.Companion.code_request.GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            initGallery(data)
//            binding.progressBarKirim.visibility = View.VISIBLE
//            binding.btnKirim.visibility = View.GONE
        }

    }

    private fun initGallery(data: Intent?) {
        val image_bitmap = onSelectFromGalleryResult(data)

        val random = Random.nextInt(0, 999999)
        var name_file = ""

        name_file = "UploadImage$random"

        mime_type = getMimeTypeFile(Uri.parse(data?.data.toString()))

        Log.d("TAG", "initGallery: MimeType : $mime_type")

        val bitmap = image_bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        if (image_path != null || image_path != "") {
            val param = RequestSendChatCS(
                sessionManager.id_message_cs,
                sessionManager.id,
                photo = encodeBase64(image_path)
            )
            viewModel.sendChatCS(param)
        } else {
            Log.d("TAG", "initGallery: ")
        }



//        uploadFiletoStorageFirebase(name_file, data)


//        binding.imageView.setImageBitmap(image_bitmap)
    }

    private fun onSelectFromGalleryResult(data: Intent?): Bitmap {
        var bm: Bitmap? = null
        if (data != null) {
            try {

                image_path = data.data?.let { FilePath.getPath(this, it) } ?: ""

                Log.d("gallery_path", image_path ?: "")

                bm =
                    MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return bm!!
    }


    private fun initCamera(data: Intent?) {
        try {
            val image = data?.extras?.get("data")
            val random = Random.nextInt(0, 999999)
            var name_file = ""

            name_file = "UploadImage$random"

            image_path = persistImage(image as Bitmap, name_file)

            mime_type = getMimeTypeFile(Uri.parse(image_path))

            Log.d("TAG", "initCamera: MimeType : $mime_type")

//            binding.imageViewFotoRekening.setImageBitmap(BitmapFactory.decodeFile(image_path))

            val bitmap = BitmapFactory.decodeFile(image_path)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            if (image_path != "" && image_path != null) {
                val param = RequestSendChatCS(
                    sessionManager.id_message_cs,
                    sessionManager.id,
                    photo = encodeBase64(image_path)
                )
                viewModel.sendChatCS(param)
            }

//            uploadFiletoStorageFirebase(name_file, data)

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Extract data included in the Intent
            data_notif = intent.getSerializableExtra("data") as ResponseNotification?

            if (data_notif != null) {
                viewModel.getChatCS(sessionManager.id_message_cs)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver,
            IntentFilter("myFunction")
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }
}