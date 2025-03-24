package com.example.final_year_coding_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Database {
    private var database: FirebaseFirestore = Firebase.firestore

    fun getFilmById(filmId: String): LiveData<Film> {
         val liveData = MutableLiveData<Film>()

         database.collection("film")
             .document(filmId)
             .get()
             .addOnSuccessListener { document ->
                 if (document.exists()) {
                     val filmFromDatabase = document.toObject(Film::class.java)
                     liveData.value = filmFromDatabase!!
                 }
             }
             .addOnFailureListener { exception ->
                 Log.e("Firestore", "Error getting documents: ", exception)
             }

         return liveData
    }
}