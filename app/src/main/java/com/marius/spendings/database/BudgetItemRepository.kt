package com.marius.spendings.database

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.models.User
import kotlinx.coroutines.tasks.await

object BudgetItemRepository {

    fun from(user: User): UserQuery {
        return UserQuery(user.id)
    }

    private suspend fun getBudgetItemsOf(userRef: DocumentReference): List<BudgetItem> {
        val expenses = userRef.collection("expenses").get().await().toObjects<BudgetItem>()
        val income = userRef.collection("income").get().await().toObjects<BudgetItem>()

        return mutableListOf(*expenses.toTypedArray(), *income.toTypedArray())
    }

    class UserQuery(userId: String) {

        private val userRef = Firebase.firestore.collection("users").document(userId)

        suspend fun getAll(): List<BudgetItem> {
            return getBudgetItemsOf(userRef)
        }
    }
}
