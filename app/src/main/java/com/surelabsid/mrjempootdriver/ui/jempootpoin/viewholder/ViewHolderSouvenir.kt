package com.surelabsid.mrjempootdriver.ui.jempootpoin.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemPilihSouvenirBinding
import com.surelabsid.mrjempootdriver.ui.jempootpoin.adapter.SouvenirAdapter
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.SouvenirItem
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_CUSTOMER_PHOTO

class ViewHolderSouvenir(
    val context: Context,
    val binding: ItemPilihSouvenirBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SouvenirItem?, itemClick: SouvenirAdapter.OnClickListener) {

        with(binding) {
            textName.text = item?.nameSouvenir
            textPoin.text = item?.poinBuy

            Glide.with(imageView8).load(BASE_URL_CUSTOMER_PHOTO + item?.imgSouvenir)
                .transform(
                    CenterCrop(),
                    RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                )
                .error(R.drawable.bg_beranda).into(imageView8)

            binding.root.setOnClickListener {
                itemClick.itemClick(item)
            }

            binding.button2.setOnClickListener {
                itemClick.itemClick(item)
            }
        }

    }
}
