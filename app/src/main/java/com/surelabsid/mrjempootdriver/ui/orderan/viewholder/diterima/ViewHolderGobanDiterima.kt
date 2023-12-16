package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.diterima

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanDiterimaBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanRiwayatBinding

class ViewHolderGobanDiterima(
    val context: Context,
    val binding: ItemOrderanGobanDiterimaBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    val pickupName = binding.appCompatTextView10
    val destinationName = binding.appCompatTextView13

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

    val buttonProcess = binding.buttonProcess
    val buttonCancel = binding.buttonCancel

    val buttonNavigation = binding.buttonNavigation

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

}
