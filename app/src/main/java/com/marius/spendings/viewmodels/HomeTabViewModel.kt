package com.marius.spendings.viewmodels

import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.ui.home.activities.USER_ID
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeTabViewModel : ViewModel() {
    @Suppress("PrivatePropertyName")
    private val TAG = "TabPageViewModel"

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

        val userDocument = Firebase.firestore.collection("users").document(USER_ID)

        val expenses = userDocument.collection("expenses").get().await().toObjects<BudgetItem>()
        val income = userDocument.collection("income").get().await().toObjects<BudgetItem>()

//        val retrievedItems = ArrayList<BudgetItem>()
        val retrievedItems = _budgetItems.value ?: ArrayList()

        return mutableListOf<BudgetItem>().apply {
            for (item in retrievedItems) {
                add(item.copy())
            }
            addAll(expenses)
            addAll(income)
            sortByDescending(BudgetItem::date)

            _isLoading.value = false
        }
    }
}