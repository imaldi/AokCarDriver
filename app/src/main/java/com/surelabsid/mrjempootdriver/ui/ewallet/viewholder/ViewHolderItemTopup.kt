package com.surelabsid.mrjempootdriver.ui.ewallet.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemEWalletBinding
import com.surelabsid.mrjempootdriver.databinding.ItemTopUpBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.formatTimeOnly
import com.surelabsid.mrjempootdriver.utils.toRupiah

class ViewHolderItemTopup(
    val context: Context,
    val binding: ItemTopUpBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WalletItem?) {
        binding.textView25.text = formatTimeOnly(item?.date)

        var item_wallet = "-"
        if (item?.type == "Topup") {
            item_wallet = "Topup"
        } else if (item?.type == "Withdraw") {
            item_wallet = "Penarikan Saldo"
        } else {
            item_wallet = "Orderan ${item?.keterangan}"
        }

        binding.textView22.text = item_wallet

        binding.textView24.text = toRupiah(item?.walletAmount?.toDouble())

    }
}
