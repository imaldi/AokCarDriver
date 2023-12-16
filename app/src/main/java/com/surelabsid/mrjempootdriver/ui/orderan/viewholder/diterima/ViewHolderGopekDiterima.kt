package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.diterima

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekDiterimaBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekRiwayatBinding

class ViewHolderGopekDiterima(
    val context: Context,
    val binding: ItemOrderanGopekDiterimaBinding,
    val parent: ViewGroup,
) :
    RecyclerView.ViewHolder(binding.root) {

//    val c = parent

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupTitle = binding.appCompatTextView9
    val pickupName = binding.appCompatTextView10

    val textViewPoint = binding.textViewPoint

    val pickupAddress = binding.appCompatTextView11

    val destinationName = binding.appCompatTextView13
    val destinationAddress = binding.appCompatTextView14

    val totalPesanan = binding.appCompatTextView16

    val textBiaya = binding.textBiaya
    val textBiayaPlatform = binding.textBiayaPlatform
    val textBiayaOngkir = binding.textBiayaOngkir
    val discount = binding.textDiscount
    val finalCost = binding.appCompatTextView18
    val finalCost1 = binding.finalCost

    val buttonProcess = binding.buttonProcess
//    val buttonCancel = binding.buttonCancel

    val buttonNavigation = binding.buttonNavigation

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

    val detailPesanan = binding.recyclerView


}
