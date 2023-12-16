package com.surelabsid.mrjempootdriver.ui.ewallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRekeningBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWalletEmpty
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderRekening
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_REKENING
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET_EMPTY
import kotlin.math.sign

class RekeningAdapter(
    private val context: Context,
    private val data: List<RekbankItem?>?,
    private val itemClick: OnClickListener
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
            ITEM_REKENING -> {
                val binding =
                    ItemRekeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderRekening(context, binding, itemClick)
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
            ITEM_REKENING -> {
                val viewHolderRekening = holder as ViewHolderRekening
                viewHolderRekening.bind(item)
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