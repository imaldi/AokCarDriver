package com.surelabsid.mrjempootdriver.ui.pendaftaran.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemBankBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.DataItem

class ListBankAdapter(
    private val context: Context,
    private val data: List<DataItem?>?,
    private val itemClick: OnClickListener?
) : RecyclerView.Adapter<ListBankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding, itemClick)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bind(item)
    }

    class ViewHolder(
        val context: Context,
        val binding: ItemBankBinding,
        val itemClick: OnClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem?) {
            binding.textBank.text = item?.nameBank

            binding.root.setOnClickListener {
                itemClick?.itemClick(item)
            }

        }
    }

    interface OnClickListener {
        fun itemClick(item: DataItem?)
    }
}