package com.surelabsid.mrjempootdriver.ui.ewallet.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemEWalletBinding
import com.surelabsid.mrjempootdriver.databinding.ItemRiwayatPenarikanBinding
import com.surelabsid.mrjempootdriver.databinding.ItemTopUpBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.formatDateOnly
import com.surelabsid.mrjempootdriver.utils.formatTimeOnly
import com.surelabsid.mrjempootdriver.utils.toRupiah

class ViewHolderItemRiwayatPenarikan(
    val context: Context,
    val binding: ItemRiwayatPenarikanBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WalletItem?) {

        with(binding) {
            textView58.text = formatDateOnly(item?.date)

            textViewNoRek.text = "${item?.keterangan}"
            textViewNamaRek.text = "a/n ${item?.driverName}"

            var status = "Pending"
            if (item?.status == "2") {
                status = "Berhasil"
            } else {
                textView60.setTextColor(ContextCompat.getColor(context, R.color.red))
                textView86.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            textView60.text = status
            textView86.text = toRupiah(item?.walletAmount?.toDouble())

        }

    }
}
