package com.surelabsid.mrjempootdriver.ui.jempootpoin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemPilihSouvenirBinding
import com.surelabsid.mrjempootdriver.databinding.ItemWalletEmptyBinding
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.RekbankItem
import com.surelabsid.mrjempootdriver.ui.ewallet.viewholder.ViewHolderItemWalletEmpty
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.SouvenirItem
import com.surelabsid.mrjempootdriver.ui.jempootpoin.viewholder.ViewHolderSouvenir
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_SOUVENIR
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_WALLET_EMPTY
import java.util.*
import kotlin.collections.ArrayList

class SouvenirAdapter(
    private val context: Context,
    private val data: List<SouvenirItem?>?,
    private val itemClick: OnClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var souvenirFilterList = ArrayList<SouvenirItem>()

    init {
        souvenirFilterList = data as ArrayList<SouvenirItem>
    }
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
            ITEM_SOUVENIR -> {
                val binding =
                    ItemPilihSouvenirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolderSouvenir(context, binding)
            }
        }
        val binding =
            ItemWalletEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return souvenirFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = souvenirFilterList[position]

        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_WALLET_EMPTY -> {
                val viewHolderItemWalletEmpty = holder as ViewHolderItemWalletEmpty
            }
            ITEM_SOUVENIR -> {
                val viewHolderSouvenir = holder as ViewHolderSouvenir
                viewHolderSouvenir.bind(item, itemClick)
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
        fun itemClick(item: SouvenirItem?)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()){
                     souvenirFilterList = data as ArrayList<SouvenirItem>
                } else {
                    val resultList = ArrayList<SouvenirItem>()
                    if (data != null) {
                        for (row in data) {
                            if (row?.nameSouvenir?.lowercase(Locale.ROOT)?.contains(charSearch.lowercase(Locale.ROOT)) == true){
                                resultList.add(row)
                            }
                        }
                        souvenirFilterList = resultList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = souvenirFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                souvenirFilterList = results?.values as ArrayList<SouvenirItem>
                notifyDataSetChanged()
            }

        }
    }

}