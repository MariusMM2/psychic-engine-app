package com.marius.spendings.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.marius.spendings.BR

/**
 * A [RecyclerView.ViewHolder] that holds a data bound item.
 *
 * @param binding the generated data binding
 */
class ItemViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Adds an object to a binding
     *
     * @param obj The object to add
     */
    fun bind(obj: Any) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }

}