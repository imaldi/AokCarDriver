package com.surelabsid.mrjempootdriver.ui.pendaftaran.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.mrjempootdriver.databinding.ItemRelationshipBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.DataItemRelationShip

class ListRelationshipAdapter(
    private val context: Context,
    private val data: List<DataItemRelationShip?>?,
    private val itemClick: OnClickListener?
) : RecyclerView.Adapter<ListRelationshipAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRelationshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding, itemClick)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bind(item)
    }

    class ViewHolder(
        val context: Context,
        val binding: ItemRelationshipBinding,
        val itemClick: OnClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItemRelationShip?) {
            binding.textViewRelationship.text = item?.relationship

            binding.root.setOnClickListener {
                itemClick?.itemClick(item)
            }

        }
    }

    interface OnClickListener {
        fun itemClick(item: DataItemRelationShip?)
    }
}