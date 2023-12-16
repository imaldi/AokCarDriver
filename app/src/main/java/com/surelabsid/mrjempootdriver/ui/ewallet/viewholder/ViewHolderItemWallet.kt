package com.surelabsid.mrjempootdriver.ui.ewallet.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemEWalletBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.toRupiah

class ViewHolderItemWallet(
    val context: Context,
    val binding: ItemEWalletBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WalletItem?) {
        binding.textDate.text = formatDate(item?.date)

        var item_orderan = "-"
        if (item?.type == "Topup") {
            item_orderan = "Topup"
        } else if (item?.type == "Withdraw") {
            item_orderan = "Penarikan Saldo"
        } else {
            item_orderan = "Orderan ${item?.keterangan}"
        }

        binding.textType.text = item_orderan

        if (item?.type == "Order+") {
            with(binding) {
                textType.setTextColor(ContextCompat.getColor(context, R.color.green))
                textAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                textAmount.text = "+ ${toRupiah(item.walletAmount?.toDouble() ?: 0.0)}"
            }
        } else if (item?.type == "Topup") {
            with(binding) {
                textType.setTextColor(ContextCompat.getColor(context, R.color.green))
                textAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                textAmount.text = "+ ${toRupiah(item.walletAmount?.toDouble() ?: 0.0)}"
            }
        } else if (item?.type == "Order-") {
            with(binding) {
                textType.setTextColor(ContextCompat.getColor(context, R.color.red))
                textAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                textAmount.text = "- ${toRupiah(item?.walletAmount?.toDouble() ?: 0.0)}"

            }
        } else if (item?.type == "Withdraw") {
            with(binding) {
                textType.setTextColor(ContextCompat.getColor(context, R.color.red))
                textAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                textAmount.text = "- ${toRupiah(item?.walletAmount?.toDouble() ?: 0.0)}"

            }
        }

    }
}
