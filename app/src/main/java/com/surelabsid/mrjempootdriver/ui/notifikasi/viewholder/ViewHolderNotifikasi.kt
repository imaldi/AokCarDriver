package com.surelabsid.mrjempootdriver.ui.notifikasi.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemNotifikasiBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanDiterimaBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanMasukBinding
import com.surelabsid.mrjempootdriver.databinding.ItemOrderanGobanRiwayatBinding
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.DataItemNotifikasi
import com.surelabsid.mrjempootdriver.utils.formatDateOnly

class ViewHolderNotifikasi(
    val context: Context,
    val binding: ItemNotifikasiBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DataItemNotifikasi?) {
        binding.textView13.text = item?.titleNotif
        binding.textView14.text = item?.textNotif
        binding.textView12.text = formatDateOnly(item?.dateNotif)
    }

}
