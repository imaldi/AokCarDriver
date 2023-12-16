package com.surelabsid.mrjempootdriver.ui.jempootpoin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRekeningBinding
import com.surelabsid.mrjempootdriver.databinding.ItemRiwayatPoinBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWalletEmpty
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderRekening
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.DataPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.WalletPoinItem
import com.surelabsid.mrjempootdriver.ui.jempootpoin.viewholder.ViewHolderPoin
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_POIN
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_REKENING
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET_EMPTY
import kotlin.math.sign

class PoinAdapter(
    private val context: Context,
    private val data: List<WalletPoinItem?>?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemView = ITEM_WALLET_EMPTY

    override fun getItemViewType(position: Int): Int {
        return itemView
    }

    fun setCurrentViewType(viewType: Int) {
        itemView = viewType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_WALLET_EMPTY-> {
                val binding =
                    ItemWalletEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderItemWalletEmpty(context, binding)
            }
            ITEM_POIN -> {
                val binding =
                    ItemRiwayatPoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderPoin(context, binding)
            }
        }
        val binding =
            ItemWalletEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data?.get(position)

        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_WALLET_EMPTY -> {
                val viewHolderItemWalletEmpty = holder as ViewHolderItemWalletEmpty
            }
            ITEM_POIN -> {
                val viewHolderPoin = holder as ViewHolderPoin
                viewHolderPoin.bind(item)
            }
        }
    }

    class ViewHolder(
        val context: Context,
        val binding: ItemWalletEmptyBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface OnClickListener {
        fun itemClick(item: RekbankItem?)
    }

}