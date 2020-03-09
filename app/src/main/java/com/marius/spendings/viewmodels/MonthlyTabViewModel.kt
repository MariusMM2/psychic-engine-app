package com.marius.spendings.viewmodels

import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.models.BudgetItem

/**
 * A ViewModel representing the state of a budget overview
 * of a specific month.
 */
class MonthlyTabViewModel: HomeTabViewModel() {

    override suspend fun BudgetItemRepository.UserQuery.fallbackListParams(): List<BudgetItem> = run {
        forMonth(2020, month = 1)
        getAll()
    }
}