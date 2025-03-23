package com.example.final_year_coding_project

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Database {
    private var database: FirebaseFirestore = Firebase.firestore

    fun getFilmById(id: String, callback: (Film?) -> Unit) {
        val docRef = database.collection("film").document(id)

        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val film = documentSnapshot.toObject(Film::class.java)
                callback(film) // Return the data through the callback
            }
            .addOnFailureListener {
                callback(null) // Handle failure case
            }
    }
}