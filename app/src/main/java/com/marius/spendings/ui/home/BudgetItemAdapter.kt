package com.marius.spendings.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.marius.spendings.R
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.ui.common.SingleLayoutAdapter
import com.marius.spendings.utils.diffResultOf

/**
 * A [SingleLayoutAdapter] that adapts [BudgetItem]s to a RecyclerView
 *
 */
class BudgetItemAdapter : SingleLayoutAdapter(R.layout.list_item_budget) {

    // List used by the adapter
    var budgetItems: List<BudgetItem> = ArrayList()
        set(value) {
            if (field.isEmpty()) {
                field = value
                this.notifyDataSetChanged()
            } else {
                val result: DiffUtil.DiffResult = diffResultOf(field, value, BudgetItem::id)

                field = value
                result.dispatchUpdatesTo(this)
            }
        }

    override fun getObjForPosition(position: Int): Any {
        return budgetItems[position]
    }

    override fun getItemCount(): Int {
        return budgetItems.size
    }
}