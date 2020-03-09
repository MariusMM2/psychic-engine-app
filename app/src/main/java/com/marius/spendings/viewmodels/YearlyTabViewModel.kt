package com.marius.spendings.viewmodels

import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.models.BudgetItem

/**
 * A ViewModel representing the state of a budget overview
 * of a specific year.
 */
class YearlyTabViewModel: HomeTabViewModel() {

    override suspend fun BudgetItemRepository.UserQuery.fallbackListParams(): List<BudgetItem> = run {
        forYear(2020)
        getAll()
    }
}