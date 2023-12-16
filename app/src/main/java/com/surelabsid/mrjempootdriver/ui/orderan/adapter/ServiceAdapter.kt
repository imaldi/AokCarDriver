package com.surelabsid.mrjempootdriver.ui.orderan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ItemServiceBinding
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItemService
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_SERVICE_IMAGE
import com.surelabsid.mrjempootdriver.utils.SessionManager

class ServiceAdapter(
    private val context: Context,
    private val data: List<DataItemService?>?,
    private val itemClick: OnClickListener?
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    private var item_index = 0

    private var sessionManager = SessionManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getPosition = position
        val item = data?.get(position)

        holder.textService.text = item?.service

        Glide.with(context)
            .load(BASE_URL_SERVICE_IMAGE+item?.icon)
            .error(R.drawable.ic_go_pek).into(holder.imageService)

        holder.button.setOnClickListener {
            item_index = getPosition
            notifyDataSetChanged()
            itemClick?.itemClick(item)
        }

        if (item_index == 0) {
            holder.button.setBackgroundResource(R.drawable.rb_yellow_r15)
            holder.textService.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            holder.textService.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        if (item_index == position) {
            holder.button.setBackgroundResource(R.drawable.rb_yellow_r15)
            holder.textService.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            holder.textService.setTextColor(ContextCompat.getColor(context, R.color.primary_color))
        } else {
            holder.button.setBackgroundResource(R.drawable.rb_purple_r15)
            holder.textService.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_color))
            holder.textService.setTextColor(ContextCompat.getColor(context, R.color.yellow))

        }

    }

    class ViewHolder(
        val context: Context,
        val binding: ItemServiceBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val button = binding.button
        val textService = binding.textService
        val imageService = binding.imageService

    }

    interface OnClickListener {
        fun itemClick(item: DataItemService?)
    }
}