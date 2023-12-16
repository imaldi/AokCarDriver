package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.masuk

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekRiwayatBinding

class ViewHolderGopekMasuk(
    val context: Context,
    val binding: ItemOrderanGopekMasukBinding,
    val parent: ViewGroup,
) :
    RecyclerView.ViewHolder(binding.root) {

    val c = parent

    val iconService = binding.iconService

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupTitle = binding.appCompatTextView9
    val pickupName = binding.appCompatTextView10

    val pickupAddress = binding.appCompatTextView11

    val textViewPoint = binding.textViewPoint

    val destinationName = binding.appCompatTextView13
    val destinationAddress = binding.appCompatTextView14

    val detailPesanan = binding.recyclerView
    val totalPesanan = binding.appCompatTextView16

    val textBiaya = binding.textBiaya
    val textBiayaPlatform = binding.textBiayaPlatform
    val textBiayaOngkir = binding.textBiayaOngkir
    val discount = binding.textDiscount
    val finalCost = binding.appCompatTextView18
    val finalCost1 = binding.finalCost

    val buttonTerima = binding.buttonTerima
    val buttonTolak = binding.buttonTolak

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

}
