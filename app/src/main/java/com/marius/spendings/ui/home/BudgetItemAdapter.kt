package com.marius.spendings.ui.home

import com.marius.spendings.R
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.ui.common.SingleLayoutAdapter

class BudgetItemAdapter: SingleLayoutAdapter(R.layout.list_item_budget) {
    lateinit var budgetItems: List<BudgetItem>

    override fun getObjForPosition(position: Int): Any {
        return budgetItems[position]
    }

    override fun getItemCount(): Int {
        return budgetItems.size
    }

}