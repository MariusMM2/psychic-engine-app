package com.marius.spendings.utils

import androidx.recyclerview.widget.DiffUtil

/**
 * Returns the difference between two lists in the form
 * of [DiffUtil.DiffResult]
 *
 * @param T type of the items in the lists
 * @param R type of the item property used for comparison
 * @param oldList the list currently used by the adapter
 * @param newList the new list to be used by the adapter
 * @param selector function to supply the comparable element of an item
 * @return difference between the lists]
 */
fun <T, R : Comparable<R>> diffResultOf(
    oldList: List<T>,
    newList: List<T>,
    selector: (T) -> R
): DiffUtil.DiffResult {
    return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compareValues(
                selector(oldList[oldItemPosition]),
                selector(newList[newItemPosition])
            ) == 0
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    })
}