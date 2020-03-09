package com.marius.spendings.viewmodels

import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.models.BudgetItem

/**
 * A ViewModel representing the state of a budget overview
 * in general.
 */
class OverviewTabViewModel: HomeTabViewModel() {

    override suspend fun BudgetItemRepository.UserQuery.fallbackListParams(): List<BudgetItem> = run {
        getAll()
    }
}