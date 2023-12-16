package com.surelabsid.mrjempootdriver.ui.callandchat

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.FragmentChatPhotoBinding

class ChatPhotoFragment : DialogFragment() {

    lateinit var binding: FragmentChatPhotoBinding

    private var is_firebase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("photo_url")

        is_firebase = arguments?.getBoolean("is_firebase") ?: false

        if (is_firebase == true){
            val storage = FirebaseStorage.getInstance()
            // Create a reference to a file from a Google Cloud Storage URI
            val gsReference = storage.getReferenceFromUrl(url ?: "")

            gsReference.downloadUrl.addOnCompleteListener {
                Glide.with(this)
                    .load(it.result)
                    .into(binding.photoView)
            }
        } else {
            Glide.with(this)
                .load(url)
                .into(binding.photoView)
        }

        binding.imageButtonBack.setOnClickListener {
            dismiss()
        }

    }
}