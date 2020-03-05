package com.marius.spendings.database

import com.marius.spendings.models.User

object UserRepository {

    val currentUser: User by lazy {
        User("alovelace", "Ada", "Lovelace")
    }
}