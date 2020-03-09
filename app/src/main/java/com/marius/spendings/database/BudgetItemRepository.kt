package com.marius.spendings.database

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marius.spendings.database.BudgetItemRepository.UserQuery
import com.marius.spendings.models.BudgetItem
import com.marius.spendings.utils.toTimestamp
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

/**
 * A repository containing delegations to [Firebase] to retrieve
 * [BudgetItem]s based on a [UserQuery]
 */
object BudgetItemRepository {
    private const val TAG = "BudgetItemRepository"

    // Default Firebase collection paths
    private const val USERS_PATH = "users"
    private const val EXPENSES_PATH = "expenses"
    private const val INCOME_PATH = "income"

    // Default Query configuration options
    // Default maximum of items to be retrieved
    private const val DEFAULT_MAX_ITEMS = 50
    // Default class field used for ordering
    private val DEFAULT_ORDER_FIELD = BudgetItem::date
    // Default String representation of the field used for ordering
    private val DEFAULT_ORDER_TARGET = DEFAULT_ORDER_FIELD.name
    // Default minimum date used for bounding
    val DEFAULT_LOWER_BOUND: LocalDate = LocalDate.of(1970, 1, 1) // 1970-01-01T00:00:00Z
    // Default maximum date used for bounding
    val DEFAULT_HIGHER_BOUND: LocalDate = LocalDate.of(9999, 1, 1) // 9999-01-01T00:00:00Z

    /**
     * Retrieved a list of [BudgetItem]s using the given [UserQuery]
     *
     * @param userQuery the query used to retrieve the items
     * @return The retrieved list of items
     */
    private suspend fun getBudgetItemsOf(userQuery: UserQuery): List<BudgetItem> {
        val collectedBudgetItems: List<BudgetItem> =
            // Check if the bounds are valid
            if (userQuery.lowerBound.isBefore(userQuery.higherBound)) {
                val expenses = retrieveByQuery(EXPENSES_PATH, userQuery)
                val income = retrieveByQuery(INCOME_PATH, userQuery)
                mutableListOf(*expenses.toTypedArray(), *income.toTypedArray())
                    .sortedByDescending(DEFAULT_ORDER_FIELD)
            } else {
                Log.e(
                    TAG, "Invalid query: lower bound '${userQuery.lowerBound}' " +
                            "cannot be after higher bound '${userQuery.lowerBound}'"
                )
                ArrayList()
            }

        if (collectedBudgetItems.isEmpty()) {
            Log.w(TAG, "No items retrieved from $userQuery")
        } else {
            Log.d(TAG, "Retrieved ${collectedBudgetItems.size} items from $userQuery")
        }

        return try {
            collectedBudgetItems
                .subList(
                    0,
                    userQuery.maxItems
                )
        } catch (e: IndexOutOfBoundsException) {
            Log.w(TAG, "Can't sublist to ${userQuery.maxItems} items of $collectedBudgetItems")
            collectedBudgetItems
        }
    }

    /**
     * Retrieves [BudgetItem] from a Firebase collection
     *
     * @param path The Firebase path of the collection
     * @param userQuery the query used to retrieve the items
     * @return The retrieved items of that collection
     */
    private suspend fun retrieveByQuery(path: String, userQuery: UserQuery): List<BudgetItem> {
        return userQuery.userRef.collection(path)
            .whereGreaterThanOrEqualTo(userQuery.orderTarget, userQuery.lowerBound.toTimestamp())
            .whereLessThan(userQuery.orderTarget, userQuery.higherBound.toTimestamp())
            .orderBy(userQuery.orderTarget, Query.Direction.DESCENDING)
            .limit(userQuery.maxItems.toLong())
            .get().await().toObjects()
    }

    /**
     * A query used to retrieve [BudgetItem]s of a specific user
     *
     * @constructor
     * Constructs a [UserQuery] using the given user id
     *
     * @param userId The id of the user used for retrieval
     */
    @Suppress("unused")
    class UserQuery(userId: String) {

        // The DocumentReference of the user in Firestore
        val userRef: DocumentReference = Firebase.firestore.collection(USERS_PATH).document(userId)

        var maxItems: Int = DEFAULT_MAX_ITEMS
            private set
        var orderTarget: String = DEFAULT_ORDER_TARGET
            private set
        var lowerBound: LocalDate = DEFAULT_LOWER_BOUND
        var higherBound: LocalDate = DEFAULT_HIGHER_BOUND

        /**
         * Gets the latest [count] items from Firestore
         *
         * @param count The maximum number of items to retrieve
         * @return The retrieved list
         */
        suspend fun getLatest(count: Int): List<BudgetItem> {
            maxItems = count
            return getAll()
        }

        /**
         * Gets the latest items from Firestore using the default limit
         *
         * @return The retrieved list
         */
        suspend fun getAll(): List<BudgetItem> {
            return getBudgetItemsOf(this)
        }

        /**
         * Sets the query to retrieve items for the entire
         * month of a year.
         *
         * @param year The year of the target month
         * @param month The month index (0-11)
         */
        fun forMonth(year: Int, month: Int) {
            lowerBound = lowerBound.withYear(year)
            higherBound = higherBound.withYear(year)
            lowerBound = lowerBound.withMonth(month + 1)
            higherBound = higherBound.withMonth(month + 2)
        }

        /**
         * Sets the query to retrieve items for an entire year.
         *
         * @param year The target year
         */
        fun forYear(year: Int) {
            lowerBound = lowerBound.withYear(year)
            higherBound = higherBound.withYear(year + 1)
        }

        /**
         * Sets the query to retrieve items starting from the given period.
         *
         * @param year The target year
         * @param month The month index (0-11)
         */
        fun from(year: Int, month: Int) {
            lowerBound = lowerBound.withMonth(month + 1).withYear(year)
        }

        /**
         * Sets the query to retrieve items until the day before the given period.
         *
         * @param year The target year
         * @param month The month index (0-11)
         */
        fun to(year: Int, month: Int) {
            higherBound = higherBound.withMonth(month + 1).withYear(year)
        }

        /**
         * Returns the default String representation of the query.
         *
         * @return The String representation of the query
         */
        override fun toString(): String {
            return "UserQuery(userRef=$userRef, maxItems=$maxItems, orderTarget='$orderTarget', lowerBound=$lowerBound, higherBound=$higherBound)"
        }


    }
}
