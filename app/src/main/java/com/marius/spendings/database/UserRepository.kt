package com.marius.spendings.database

import com.google.firebase.ktx.Firebase
import com.marius.spendings.models.User

/**
 * A repository containing delegations to [Firebase] to retrieve
 * the current logged user.
 */
object UserRepository {

    // The current user logged in the app
    val currentUser: User by lazy {
        User("alovelace", "Ada", "Lovelace")
    }
}