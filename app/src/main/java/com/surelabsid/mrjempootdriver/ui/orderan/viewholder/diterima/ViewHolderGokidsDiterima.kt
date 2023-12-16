package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.diterima

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanRiwayatBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGokidsDiterimaBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGokidsMasukBinding

class ViewHolderGokidsDiterima(
    val context: Context,
    val binding: ItemOrderanGokidsDiterimaBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupAddress = binding.appCompatTextView11
    val destinationAddress = binding.appCompatTextView14

    val textViewPoint = binding.textViewPoint
    val textTrip = binding.textTrip
    val distance = binding.textDistance

    val textWaktuDropoff = binding.textWaktuDropoff

    val dateRangeSubscribe = binding.dateRangeSubscribe
    val totalHari = binding.totalHari
    val textViewTarifPerhari = binding.textViewTarifPerhari

    val finalCost = binding.textfinalCost
    val tarif = binding.textViewTarifJemput
    val titleTarif = binding.textView100

    val buttonProcess = binding.buttonProcess
    val buttonCancel = binding.buttonCancel

    val buttonNavigation = binding.buttonNavigation

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall
    val buttonLive = binding.imageButtonCall2

}
