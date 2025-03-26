package com.example.final_year_coding_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
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
                     filmFromDatabase?.setId(document.id)
                     liveData.value = filmFromDatabase!!
                 }
             }
             .addOnFailureListener { exception ->
                 Log.e("Firestore", "Error getting documents: ", exception)
             }

         return liveData
    }

    fun addLikeToFilmById(filmId: String){
        database.collection("film").document(filmId)
            .update("likes", FieldValue.increment(1))
    }

    fun removeLikeFromFilmById(filmId: String) {
        database.collection("film").document(filmId)
            .update("likes", FieldValue.increment(-1))
    }

    fun addDislikeToFilmById(filmId: String){
        database.collection("film").document(filmId)
            .update("dislikes", FieldValue.increment(1))
    }

    fun removeDislikeFromFilmById(filmId: String) {
        database.collection("film").document(filmId)
            .update("dislikes", FieldValue.increment(-1))
    }
}