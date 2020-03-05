package com.marius.spendings.database

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.models.User
import kotlinx.coroutines.tasks.await

object BudgetItemRepository {

    private const val USERS_PATH = "users"
    private const val EXPENSES_PATH = "expenses"
    private const val INCOME_PATH = "income"

    private const val DEFAULT_MAX_ITEMS = 50
    private val DEFAULT_ORDER_FIELD = BudgetItem::date
    private val DEFAULT_ORDER_TARGET = DEFAULT_ORDER_FIELD.name

    fun from(user: User): UserQuery {
        return UserQuery(user.id)
    }

    private suspend fun getBudgetItemsOf(userQuery: UserQuery): List<BudgetItem> {
        val expenses = retrieveByQuery(EXPENSES_PATH, userQuery)
        val income = retrieveByQuery(INCOME_PATH, userQuery)

        return mutableListOf(*expenses.toTypedArray(), *income.toTypedArray())
            .sortedByDescending(DEFAULT_ORDER_FIELD)
            .subList(0, userQuery.maxItems)
    }

    private suspend fun retrieveByQuery(path: String, userQuery: UserQuery): List<BudgetItem> {
        return userQuery.userRef.collection(path)
            .orderBy(userQuery.orderTarget, Query.Direction.DESCENDING)
            .limit(userQuery.maxItems.toLong())
            .get().await().toObjects()
    }

    class UserQuery(userId: String) {

        val userRef = Firebase.firestore.collection(USERS_PATH).document(userId)
        var maxItems: Int = DEFAULT_MAX_ITEMS
            private set
        var orderTarget: String = DEFAULT_ORDER_TARGET
            private set

        suspend fun getLatest(count: Int): List<BudgetItem> {
            maxItems = count
            return getAll()
        }

        suspend fun getAll(): List<BudgetItem> {
            return getBudgetItemsOf(this)
        }
    }
}
