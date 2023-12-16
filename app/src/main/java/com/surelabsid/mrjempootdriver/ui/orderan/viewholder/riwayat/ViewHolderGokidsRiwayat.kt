package com.surelabsid.mrjempootdriver.ui.orderan.viewholder.riwayat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGokidsRiwayatBinding

class ViewHolderGokidsRiwayat(
    val context: Context,
    val binding: ItemOrderanGokidsRiwayatBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    val photoCustomer = binding.imageView
    val nameCustomer = binding.textViewNama
    val kodePesanan = binding.textViewKodePesanan

    val imageTypePayment = binding.imageTypePayment
    val typePayment = binding.typePayment

    val pickupTitle = binding.appCompatTextView9
    val pickupName = binding.appCompatTextView10

    val textViewPoint = binding.textViewPoint
    val textTrip = binding.textTrip
    val pickupAddress = binding.appCompatTextView11
    val textWaktuDropoff = binding.textWaktuDropoff

    val dateRangeSubscribe = binding.dateRangeSubscribe
    val totalHari = binding.totalHari
    val textViewTarifPerhari = binding.textViewTarifPerhari
    val textViewTitleTarifPerhari = binding.textView101

    val pickupIcon = binding.appCompatImageView6

    val destinationTitle = binding.appCompatTextView12
    val destinationName = binding.appCompatTextView13
    val destinationAddress = binding.appCompatTextView14
    val destinationIcon = binding.appCompatImageView7

//    val trip = binding.textTrip

    val distance = binding.textDistance

    val finalCost = binding.textfinalCost
    val tarif = binding.textViewTarifJemput
    val titleTarif = binding.textView100

    val tanggalOrderan = binding.textTanggalOrderan

    val textGetPoin = binding.textView90

    val buttonNilai = binding.buttonNilai

    val buttonChat = binding.imageButtonChat
    val buttonCall = binding.imageButtonCall

    val buttonLive = binding.imageButtonCall2

}
