package com.surelabsid.mrjempootdriver.ui.ulasan.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRekeningBinding
import com.surelabsid.mrjempootdriver.databinding.ItemUlasanBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.adapter.RekeningAdapter
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.ulasan.adapter.UlasanAdapter
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.DetailItem
import com.surelabsid.mrjempootdriver.utils.formatDateOnly

class ViewHolderUlasan(
    val context: Context,
    val binding: ItemUlasanBinding
) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetailItem?) {

            with(binding) {
                viewRating.rating = item?.rating?.toFloat() ?: 0f
                textNote.text = item?.note
                textDate.text = "${formatDateOnly(item?.updateAt)} oleh ${item?.customerFullname}"

            }

        }
}
