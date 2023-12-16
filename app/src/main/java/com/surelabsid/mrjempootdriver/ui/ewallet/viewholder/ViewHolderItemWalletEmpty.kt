package com.surelabsid.mrjempootdriver.ui.ewallet.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemEWalletBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.toRupiah

class ViewHolderItemWalletEmpty(
    val context: Context,
    val binding: ItemWalletEmptyBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

}
