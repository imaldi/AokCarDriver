package com.surelabsid.mrjempootdriver.ui.butuhbantuan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.FirebaseStorage
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemChatBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.DetailChatItemCS
import com.surelabsid.mrjempootdriver.ui.callandchat.model.SendChat
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWalletEmpty
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.formatDate
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatCSAdapter(val idUserActive: String, val messages: List<DetailChatItemCS?>?, val onClick: OnClick?) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ChatCSAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view, idUserActive)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        messages?.get(position)?.let { holder.bindForecast(it) }
    }

    override fun getItemCount() = messages?.size ?: 0

    interface OnClick {
        fun onImageClick(urlPhoto: String)
    }

    inner class ViewHolder(val binding: ItemChatBinding, val idUser: String) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bindForecast(message: DetailChatItemCS) {
            with(message) {
                val instance = SimpleDateFormat("yyyy-MM-dd HH:mm")
//                val date = Date(timestamp)
//                val dateString = instance.format(message.createdAt)

                with(binding) {
                    if (message.idUser == idUserActive){

                        linearTheir.visibility = View.GONE
                        linearMe.visibility = View.VISIBLE

                        cardMe.setCardBackgroundColor(android.R.color.background_dark)
                        messageDatetimeMe.text = formatDate(message.createdAt)

                        if (message.photo != "") {
                            imageMe.visibility = View.VISIBLE

                            Glide.with(imageMe).load(Constant.Companion.url.BASE_URL_CHATCS_PHOTO + message.photo)
                                .transform(
                                    CenterCrop(),
                                )
                                .error(R.drawable.bg_beranda).into(imageMe)

//                            val storage = FirebaseStorage.getInstance()
//                            // Create a reference to a file from a Google Cloud Storage URI
//                            val gsReference = storage.getReferenceFromUrl("gs://mr-jempoot.appspot.com/images/${message.photo}")
//
//                            gsReference.downloadUrl.addOnCompleteListener {
//                                Glide.with(imageMe)
//                                    .load(it.result)
//                                    .into(imageMe)
//                            }

                            imageMe.setOnClickListener { onClick?.onImageClick(Constant.Companion.url.BASE_URL_CHATCS_PHOTO + message.photo) }

                        } else {
                            imageMe.visibility = View.GONE
                        }

                        if (message.message != null) {
                            messageMe.text = message.message
                        } else {
                            messageMe.visibility = View.GONE
                        }
                    } else {
                        linearTheir.visibility = View.VISIBLE
                        linearMe.visibility = View.GONE

                        messageDatetimeTheir.text = formatDate(message.createdAt)

                        if (message.photo != "") {
                            imageTheir.visibility = View.VISIBLE

                            Glide.with(imageTheir).load(Constant.Companion.url.BASE_URL_CHATCS_PHOTO + message.photo)
                                .transform(
                                    CenterCrop(),
                                )
                                .error(R.drawable.bg_beranda).into(imageTheir)

                            imageTheir.setOnClickListener { onClick?.onImageClick(Constant.Companion.url.BASE_URL_CHATCS_PHOTO + message.photo) }

//                            val urlPhoto = "$BASE_URL_UPLOADED_IMAGES/chats/${message.photo}"

//                            Picasso.get()
//                                .load(urlPhoto)
//                                .into(itemView.imageTheir)
//                            imageTheir.setOnClickListener { onClick?.onImageClick(urlPhoto) }
                        } else {
                            imageTheir.visibility = View.GONE
                        }
                        if (message.message != null) {
                            messageTheir.text = message.message
                        } else {
                            messageTheir.visibility = View.GONE
                        }
                    }
                }

            }
        }
    }
}