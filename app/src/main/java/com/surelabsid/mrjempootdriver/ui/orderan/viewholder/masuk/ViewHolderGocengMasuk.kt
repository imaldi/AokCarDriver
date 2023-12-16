package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.masuk

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGocengMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGocengRiwayatBinding

class ViewHolderGocengMasuk(
    val context: Context,
    val binding: ItemOrderanGocengMasukBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    val iconService = binding.iconService

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupAddress = binding.appCompatTextView11
    val destinationAddress = binding.appCompatTextView14

    val textViewPoint = binding.textViewPoint

    val trip = binding.textTrip

    val distance = binding.textDistance

    val finalCost = binding.textfinalCost
    val tarif = binding.textViewTarifJemput
    val titleTarif = binding.textView100

    val buttonTerima = binding.buttonTerima
    val buttonTolak = binding.buttonTolak

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

}
