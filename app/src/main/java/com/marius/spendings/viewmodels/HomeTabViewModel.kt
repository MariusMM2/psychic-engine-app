package com.marius.spendings.viewmodels

import androidx.lifecycle.*
import com.marius.spendings.database.BudgetItemRepository
import com.marius.spendings.database.UserRepository
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.models.User
import kotlinx.coroutines.launch

class HomeTabViewModel : ViewModel() {

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = "TabPageViewModel"

    private val _currentUser = MutableLiveData(UserRepository.currentUser)
    private val currentUser: LiveData<User?> = _currentUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _budgetItems = liveData {
        val listBudgetItems = loadBudgetItems()
        emit(listBudgetItems)
    } as MutableLiveData<List<BudgetItem>>
    val budgetItems: LiveData<List<BudgetItem>> = _budgetItems

    fun reloadBudgetItems() {
        viewModelScope.launch {
            val listBudgetItems = loadBudgetItems()
            _budgetItems.postValue(listBudgetItems)
        }
    }

    private suspend fun loadBudgetItems(): List<BudgetItem> {
        _isLoading.value = true

        val result: ArrayList<BudgetItem> = ArrayList()

        currentUser.value!!.let { user ->
            result.addAll(BudgetItemRepository.from(user).getLatest(5))
        }

        return result.also {
            _isLoading.value = false
        }
    }

    fun setUser(currentUser: User) {
        _currentUser.value = currentUser
    }
}