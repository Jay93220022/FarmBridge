package com.example.farmbridge.ui.theme.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CustomerRepository(private val firestore: FirebaseFirestore) {

    suspend fun authenticateCustomer(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            firestore.collection("customers")
                .whereEqualTo("ID", email)
                .whereEqualTo("password", password) // This is just for demo; store hashed passwords securely!
                .get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot.documents.isNotEmpty())
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
