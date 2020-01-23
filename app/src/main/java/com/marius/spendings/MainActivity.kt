package com.marius.spendings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    @Suppress("PrivatePropertyName")
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val user = HashMap<String, Any>()

        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference: DocumentReference? ->
                Log.d(
                    TAG,
                    documentReference?.id ?: ""
                )
            }
            .addOnFailureListener { exception -> Log.w(TAG, "Error adding document", exception) }

    }
}
