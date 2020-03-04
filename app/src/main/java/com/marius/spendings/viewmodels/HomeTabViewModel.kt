package com.marius.spendings.viewmodels

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.ui.home.activities.USER_ID

class HomeTabViewModel : ViewModel() {
    @Suppress("PrivatePropertyName")
    private val TAG = "TabPageViewModel"

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _budgetItems = MutableLiveData<List<BudgetItem>>()
    val budgetItems: LiveData<List<BudgetItem>> = _budgetItems

    fun loadBudgetItems() {
        _isLoading.value = true

        var loadingCount = 2
        var retrievedItems = ArrayList<BudgetItem>()
//        var retrievedItems = _budgetItems.value ?: ArrayList()

        //TODO Move logic into a database handler
        val querySuccessListener = OnSuccessListener<QuerySnapshot> {
            val oldItems: List<BudgetItem> = retrievedItems
            val newItems: ArrayList<BudgetItem> = ArrayList(oldItems.size)

            for (item in oldItems) {
                newItems.add(item.copy())
            }

            val elements = it.toObjects<BudgetItem>()
            Log.d(TAG, "Retrieved items: $elements")
            newItems.addAll(elements)
            retrievedItems = newItems

            loadingCount--
            if (loadingCount == 0) {
                Handler().postDelayed({
                    _budgetItems.value =
                        retrievedItems.sortedByDescending { budgetItem -> budgetItem.date }
                    _isLoading.value = false
                }, 1000)
            }
        }

        Firebase.firestore.collection("users").document(USER_ID).apply {
            collection("expenses")
                .get()
                .addOnSuccessListener(querySuccessListener)
            collection("income")
                .get()
                .addOnSuccessListener(querySuccessListener)
        }
    }
}