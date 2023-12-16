package com.surelabsid.mrjempootdriver.ui.ewallet.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRekeningBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.RekeningAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem

class ViewHolderRekening(
    val context: Context,
    val binding: ItemRekeningBinding,
    val itemClick: RekeningAdapter.OnClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RekbankItem?) {

            with(binding) {
                namaBank.text = item?.nameBank
                namaRek.text = "${item?.noRek}-${item?.namaRek} "

                binding.root.setOnClickListener {
                    itemClick.itemClick(item)
                }
            }


        }
}
