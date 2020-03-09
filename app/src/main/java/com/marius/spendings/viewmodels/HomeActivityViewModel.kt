package com.marius.spendings.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.models.Price

/**
 * A ViewModel representing the state of the pinned display
 * for the currently selected overview.
 */
class HomeActivityViewModel: ViewModel() {

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = "HomeActivityViewModel"

    // Loading state of the overview
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // List of budget items of the overview
    private var _budgetItems = MutableLiveData<List<BudgetItem>>()
    val budgetItems: LiveData<List<BudgetItem>> = _budgetItems

    // Sum of the prices in the list
    private val _price = MutableLiveData<Price>()
    val price: LiveData<Price> = _price

    // The overview ID that is currently displayed
    private val _source = MutableLiveData("")
    val source: LiveData<String> = _source

    /**
     * Changes the list items with the ones from the source,
     * provided the source is the one currently displayed.
     *
     * @param items The new items to be saved
     * @param source The source of the items
     */
    fun setItems(items: List<BudgetItem>, source: String) {
        if (_source.value != source) {
            Log.d(TAG, "Rejected '$source' (expected '${_source.value}')")
            return
        } else {
            Log.d(TAG, "Accepted '$source'")
        }

        _budgetItems.value = items
        if (items.isNotEmpty()) calculateTotal()
    }

    /**
     * Changes the loading state with the one from the source,
     * provided the source is the one currently displayed.
     *
     * @param loading The new loading state to be saved
     * @param source The source of the items
     */
    fun setLoading(loading: Boolean, source: String) {
        if (_source.value != source) {
            Log.d(TAG, "Rejected '$source' (expected '${_source.value}')")
            return
        } else {
            Log.d(TAG, "Accepted '$source'")
        }
        Log.d(TAG, "Loading: $loading")
        _isLoading.value = loading
    }

    /**
     * Changes the current source identifier to the one provided
     *
     * @param source The new source identifier
     */
    fun setItemsSource(source: String) {
        Log.d(TAG, "Source set to $source")
        _source.value = source
    }

    /**
     * Calculates the sum of the prices in the list
     */
    private fun calculateTotal() {
        val newPrice = Price()

        _budgetItems.value!!.forEach {
            newPrice.value += it.price.value
        }

        _price.value = newPrice
        Log.d(TAG, "new amount: ${newPrice.value}")
    }
}