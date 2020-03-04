@file:Suppress("unused")

package com.marius.spendings.models

import com.google.firebase.Timestamp
import java.util.*

/**
 * Object that holds data related to a budget entry
 *
 * @property id identifier of the object
 * @property description summary about the transaction
 * @property quantity number of items in the transaction
 * @property price price of an item
 * @property date date of the transaction
 * @property category category of the transaction
 * @property hideInStats whether to ignore the entry when calculating statistics
 */
data class BudgetItem(
    val id: String,
    var description: String,
    var quantity: Int = 1,
    var price: Price,
    var date: Timestamp,
    var category: String = "",
    var hideInStats: Boolean = false
) {
    constructor() : this("", "", 0, Price(), Timestamp(Date()), "", false)
}

/**
 * Object that hold data related about the pricing of a transaction
 *
 * @property value monetary value of the item
 * @property currency the currency used
 * @property ratioToEuro the currency ratio to the Euro at the time of the transaction
 */
data class Price(
    var value: Int,
    var currency: String,
    var ratioToEuro: Float = 1f
) {
    constructor() : this(0, "", 0f)
}