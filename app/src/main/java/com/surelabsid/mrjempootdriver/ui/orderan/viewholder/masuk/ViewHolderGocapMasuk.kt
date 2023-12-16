package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.masuk

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGocapMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGocapRiwayatBinding

class ViewHolderGocapMasuk(
    val context: Context,
    val binding: ItemOrderanGocapMasukBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    val senderName = binding.appCompatTextView10
    val receiverName = binding.appCompatTextView13
    val detailSenderName = binding.detailSenderName
    val detailSenderPhone = binding.detailSenderPhone
    val detailSenderAddress = binding.detailSenderAddress

    val detailReceiverName = binding.detailReceiverName
    val detailReceiverPhone = binding.detailReceiverPhone
    val detailReceiverAddress = binding.detailReceiverAddress

    val jenisPaket = binding.jenisPaket
    val beratPaket = binding.beratPaket
    val biayaEkstra = binding.biayaEktra

    val iconService = binding.iconService

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupAddress = binding.appCompatTextView11
    val destinationAddress = binding.appCompatTextView14

    val textViewPoint = binding.textViewPoint

    val trip = binding.textView80

    val finalCost = binding.textfinalCost

    val buttonTerima = binding.buttonTerima
    val buttonTolak = binding.buttonTolak

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

}
