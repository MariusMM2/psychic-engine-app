package com.marius.spendings.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A [RecyclerView.Adapter] that assigns a collection of
 * data bound objects to [ItemViewHolder]s.
 */
abstract class ItemBaseAdapter: RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val obj: Any = getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    abstract fun getObjForPosition(position: Int): Any

    abstract fun getLayoutIdForPosition(position: Int): Int

}