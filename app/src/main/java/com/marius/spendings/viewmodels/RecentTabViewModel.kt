package com.marius.spendings.viewmodels

import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.models.BudgetItem

/**
 * A ViewModel representing the state of a budget overview
 * for a recent period of time.
 */
class RecentTabViewModel: HomeTabViewModel() {

    override suspend fun BudgetItemRepository.UserQuery.fallbackListParams(): List<BudgetItem> = run {
        forYear(2020)
        getLatest(5)
    }
}