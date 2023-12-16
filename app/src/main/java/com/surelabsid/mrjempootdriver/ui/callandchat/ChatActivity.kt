package com.surelabsid.mrjempootdriver.ui.callandchat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityChatBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.callandchat.adapter.ChatAdapter
import com.surelabsid.mrjempootdriver.ui.callandchat.model.SendChat
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.GALLERY_CODE
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.random.Random

@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    private lateinit var binding: ActivityChatBinding

    private lateinit var database: DatabaseReference

    private lateinit var storageFirebase: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private var item: DataItem? = null

    private var is_attach = false

    private var image_path: String? = null

    private var mime_type: String? = null

    private var chatPhotoFragment = ChatPhotoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = intent.getSerializableExtra("item") as DataItem

        binding.progressBarChat.visibility = View.VISIBLE

        initFirebase()

        initPermissionStorageAndCameraAndCall()

        initView()

        loadChat()
    }

    private fun loadChat() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toReturn: ArrayList<SendChat> = ArrayList()

                for (data in snapshot.children) {
                    val messageData = data?.getValue<SendChat>(SendChat::class.java)

                    val message = messageData?.let { it } ?: continue

                    toReturn.add(message)
                }

                //sort so newest at bottom
                toReturn.sortBy { message ->
                    message.timestamp
                }

                if (!toReturn.isNullOrEmpty()) {
                    setupAdapter(toReturn)
                }

                binding.progressBarChat.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "onCancelled: ", error.toException())
            }

        }
        database.child(item?.id.toString()).addValueEventListener(postListener)
    }

    private fun initFirebase() {

        //get reference to our db
        database = FirebaseDatabase.getInstance().reference
//        database = Firebase.database.reference

        storageFirebase = Firebase.storage
        storageReference = storageFirebase.reference

    }

    private fun setupAdapter(data: ArrayList<SendChat>) {

        Log.d("Size chat", data.size.toString())

        binding.recyclerViewChat.adapter =
            ChatAdapter("${sessionManager.id}", data, object : ChatAdapter.OnClick {
                override fun onImageClick(urlPhoto: String) {

                    val bundle = Bundle()
                    bundle.putString("photo_url", urlPhoto)
                    bundle.putBoolean("is_firebase", true)
                    chatPhotoFragment.arguments = bundle
                    chatPhotoFragment.show(supportFragmentManager, "")

                }
            })

        //scroll to bottom
        binding.recyclerViewChat.scrollToPosition(data.size - 1)
    }


    private fun initView() {
        with(binding) {

            btnAttach.setOnClickListener {
                is_attach = is_attach == false
                setDialog()
            }

            imageButtonCall.setOnClickListener {

                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + "+"+item?.teleponPelanggan)
                startActivity(callIntent)

            }

            textDriver.text = item?.customerFullname

            imageButtonBack.setOnClickListener {
                finish()
            }
            btnKirim.setOnClickListener {
                progressBarKirim.visibility = View.VISIBLE
                btnKirim.visibility = View.VISIBLE

                sendChat(item?.id.toString(), sessionManager.id, textMessage.text.toString())
            }

            binding.btnCamera.setOnClickListener {
                openCamera()
            }

            binding.btnGallery.setOnClickListener {
//                val galleryIntent = Intent(
//                    Intent.ACTION_PICK,
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                startActivityForResult(galleryIntent, GALLERY_CODE)

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

    private fun sendChat(kode: String, id_user: String, message: String) {
        val chat = SendChat(id_user, message)
        database.child(kode).child(System.currentTimeMillis().toString()).setValue(chat)
        binding.textMessage.setText("")
        binding.progressBarKirim.visibility = View.GONE
        binding.btnKirim.visibility = View.VISIBLE


    }

    private fun sendImage(kode: String, id_user: String, photo: String) {
        val chat = SendChat(id_user, photo = photo)
        database.child(kode).child(System.currentTimeMillis().toString()).setValue(chat)
        binding.textMessage.setText("")
        binding.progressBarKirim.visibility = View.GONE
        binding.btnKirim.visibility = View.VISIBLE

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == Constant.Companion.code_request.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            initCamera(data)
            binding.progressBarKirim.visibility = View.VISIBLE
            binding.btnKirim.visibility = View.GONE
        } else if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            initGallery(data)
            binding.progressBarKirim.visibility = View.VISIBLE
            binding.btnKirim.visibility = View.GONE
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

        uploadFiletoStorageFirebase(name_file, data)


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

            uploadFiletoStorageFirebase(name_file, data)

        } catch (e: Exception) {
            Log.d("Error", "initCameraException: $e")
        }
    }

    private fun uploadFiletoStorageFirebase(name_file: String, data: ByteArray) {
        val imageRef = storageReference.child("images/$name_file")

        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnSuccessListener {
            sendImage(item?.id.toString(), sessionManager.id, name_file)
            showToast(this, "berhasil kirim gambar")
        }.addOnFailureListener {
            Log.d("TAG", "initCamera: $it")
        }
    }
}