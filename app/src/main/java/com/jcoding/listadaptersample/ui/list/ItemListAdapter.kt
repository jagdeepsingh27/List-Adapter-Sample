package com.jcoding.listadaptersample.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jcoding.listadaptersample.customviews.CustomListItemView
import com.jcoding.listadaptersample.data.model.ListItemData
import com.jcoding.listadaptersample.databinding.CustomListItemViewLayoutBinding
import com.jcoding.listadaptersample.databinding.ItemListAdapterItemLayoutBinding

class ItemListAdapter() : ListAdapter<ListItemData, ItemListAdapter.ItemViewHolder>(
    ItemListAdapterDiffCallback
) {
    interface Callback {
        fun onStatusIconClick(item: ListItemData)
    }
    private var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListAdapterItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position), ListItemPayloadDiff.getFullPayload())
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        val diff = (
//                payloads.filterIsInstance<ListItemPayloadDiff>()
//                    .takeIf { it.isNotEmpty() }
//                    ?: listOf(ListItemPayloadDiff.getFullPayload())
//                ).fold(ListItemPayloadDiff.getEmptyPayload(), ListItemPayloadDiff::plus)

        val payload = payloads.firstOrNull() as? ListItemPayloadDiff ?: ListItemPayloadDiff.getFullPayload()
        holder.onBind(getItem(position), payload)
    }

    inner class ItemViewHolder(val binding: ItemListAdapterItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: ListItemData

        init {
            binding.listItemView.setCallback(object : CustomListItemView.Callback{
                override fun onStatusIconClick() {
                     callback?.onStatusIconClick(item)
                }
            })
        }

        fun onBind(_item: ListItemData, payload: ListItemPayloadDiff) {
            this.item = _item
            binding.listItemView.setContent(item, payload)
        }

    }

    fun setCallback(_callback: Callback) {
        callback = _callback
    }
}