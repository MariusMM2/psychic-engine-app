package com.marius.spendings.viewmodels

import androidx.lifecycle.*
import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * An abstract ViewModel representing the state of a budget
 * overview of a specific criterion.
 */
abstract class HomeTabViewModel : ViewModel() {

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = "TabPageViewModel"

    // Current logged user
    private val _currentUser = MutableLiveData<User>()
    private val currentUser: LiveData<User?> = _currentUser

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // List of budget items
    private var _budgetItems = liveData {
        val listBudgetItems = loadBudgetItems()
        emit(listBudgetItems)
    } as MutableLiveData<List<BudgetItem>>
    val budgetItems: LiveData<List<BudgetItem>> = _budgetItems

    /**
     * Calls a coroutine to reload the list
     */
    fun reloadBudgetItems() {
        viewModelScope.launch {
            val listBudgetItems = loadBudgetItems()
            _budgetItems.postValue(listBudgetItems)
        }
    }

    /**
     * Suspendable function used to define the list query
     *
     * @return The list retrieved using the defined query
     */
    protected abstract suspend fun BudgetItemRepository.UserQuery.fallbackListParams(): List<BudgetItem>

    /**
     * Retrieves a list of [BudgetItem]s from the repository
     *
     * @return The retrieved list
     */
    private suspend fun loadBudgetItems(): List<BudgetItem> {
        _isLoading.value = true

        return (currentUser.value?.let { user ->
            delay(1000)
            BudgetItemRepository.UserQuery(user.id).fallbackListParams()
        } ?: ArrayList()).also {
            _isLoading.value = false
        }
    }

    /**
     * Sets the current logged user
     *
     * @param currentUser The new user
     */
    fun setUser(currentUser: User) {
        _currentUser.value = currentUser
    }
}