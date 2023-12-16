package com.surelabsid.mrjempootdriver.ui.ewallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemEWalletBinding
import com.surelabsid.mrjempootdriver.databinding.ItemRiwayatPenarikanBinding
import com.surelabsid.mrjempootdriver.databinding.ItemTopUpBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.WalletItem
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemRiwayatPenarikan
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemTopup
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWalletEmpty
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_PENARIKAN
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_TOPUP
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET_EMPTY
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.toRupiah
import okhttp3.internal.ignoreIoExceptions

class HistoryWalletAdapter(
    private val context: Context,
    private val data: List<WalletItem?>?,
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
            ITEM_WALLET -> {
                val binding =
                    ItemEWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderItemWallet(context, binding)
            }
            ITEM_TOPUP -> {
                val binding =
                    ItemTopUpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderItemTopup(context, binding)
            }
            ITEM_PENARIKAN -> {
                val binding =
                    ItemRiwayatPenarikanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderItemRiwayatPenarikan(context, binding)
            }
        }
        val binding =
            ItemEWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            ITEM_WALLET -> {
                val viewHolderItemWallet = holder as ViewHolderItemWallet
                viewHolderItemWallet.bind(item)
            }
            ITEM_TOPUP -> {
                val viewHolderItemTopup = holder as ViewHolderItemTopup
                viewHolderItemTopup.bind(item)
            }
            ITEM_PENARIKAN -> {
                val viewHolderItemRiwayatPenarikan = holder as ViewHolderItemRiwayatPenarikan
                viewHolderItemRiwayatPenarikan.bind(item)
            }

        }
    }

    class ViewHolder(
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

}