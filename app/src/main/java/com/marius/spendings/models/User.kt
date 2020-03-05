package com.marius.spendings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val id: String,
    var firstName: String,
    var lastName: String
) : Parcelable {
    @Suppress("unused")
    constructor() : this("", "", "")
}