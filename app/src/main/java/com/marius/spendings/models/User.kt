@file:Suppress("unused")

package com.marius.spendings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Object that holds data related to the representation of a logged user
 *
 * @property id identifier of the user
 * @property firstName first name of the user
 * @property lastName last name of the user
 */
@Parcelize
data class User (
    val id: String,
    var firstName: String,
    var lastName: String
) : Parcelable {
    constructor() : this("", "", "")
}