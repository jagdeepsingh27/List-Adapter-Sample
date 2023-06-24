package com.jcoding.listadaptersample.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.jcoding.listadaptersample.data.model.ListItemData

object  ItemListAdapterDiffCallback : DiffUtil.ItemCallback<ListItemData>() {

    override fun areItemsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean {
        return oldItem.diff(newItem).hasDifference().not()
    }

    override fun getChangePayload(oldItem: ListItemData, newItem: ListItemData): Any? {
        return oldItem.diff(newItem)
    }
}