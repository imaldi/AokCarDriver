package com.surelabsid.mrjempootdriver.ui.jempootpoin.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRekeningBinding
import com.surelabsid.mrjempootdriver.databinding.ItemRiwayatPoinBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.RekeningAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.DataPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.WalletPoinItem
import com.surelabsid.mrjempootdriver.utils.formatDateOnly

class ViewHolderPoin(
    val context: Context,
    val binding: ItemRiwayatPoinBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WalletPoinItem?) {

        with(binding) {
            textView52.text = formatDateOnly(item?.dateWalletPoin)
            textView53.text = item?.keteranganPoin
            textView54.text = item?.amountPoin
        }

    }
}
