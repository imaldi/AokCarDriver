package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.riwayat

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGopekRiwayatBinding

class ViewHolderGopekRiwayat(
    val context: Context,
    val binding: ItemOrderanGopekRiwayatBinding,
    val parent: ViewGroup,
) :
    RecyclerView.ViewHolder(binding.root) {

    //Goceng Riwayat
    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupTitle = binding.appCompatTextView9
    val pickupName = binding.appCompatTextView10

    val textViewPoint = binding.textViewPoint

//    val pickupAddress = binding.appCompatTextView11

    val pickupIcon = binding.appCompatImageView6

    val destinationTitle = binding.appCompatTextView12
    val destinationName = binding.appCompatTextView13
//    val destinationAddress = binding.appCompatTextView14
    val destinationIcon = binding.appCompatImageView7

    val detailPesanan = binding.recyclerView
    val totalPesanan = binding.appCompatTextView16


//    val tarif = binding.textView100
//    val titleTarif = binding.textView100
    val textBiaya = binding.textBiaya
    val textBiayaPlatform = binding.textBiayaPlatform
    val textBiayaOngkir = binding.textBiayaOngkir
    val discount = binding.textDiscount
    val finalCost = binding.appCompatTextView18
    val finalCost1 = binding.finalCost
    val tanggalOrderan = binding.textTanggalOrderan

    val textGetPoin = binding.textView90

    val buttonNilai = binding.buttonNilai

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

}
