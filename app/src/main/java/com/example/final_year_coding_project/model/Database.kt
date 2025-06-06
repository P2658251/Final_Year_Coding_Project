package com.example.final_year_coding_project.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Database {
    private val database: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getFilmByKey(filmKey: String): LiveData<Film> {
         val liveData = MutableLiveData<Film>()

         database.collection("film")
             .document(filmKey)
             .get()
             .addOnSuccessListener { document ->
                 if (document.exists()) {
                     val filmFromDatabase = document.toObject(Film::class.java)
                     filmFromDatabase?.setKey(document.id)
                     liveData.value = filmFromDatabase!!
                 }
             }
             .addOnFailureListener { exception ->
                 Log.e("Firestore", "Error getting documents: ", exception)
             }

         return liveData
    }

    fun addUser(user: User){
        database.collection("user").document(user.getUsername()).set(user)
    }

    fun getUserByKey(userKey: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        if(userKey.isNotEmpty()){
            database.collection("user")
                .document(userKey)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userFromDatabase = document.toObject(User::class.java)
                        liveData.value = userFromDatabase!!
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Firestore", "Error getting documents: ", exception)
                }
        }
        return liveData
    }

    fun addLikeToFilmById(filmId: String, username: String){
        database.collection("film").document(filmId)
            .update("likes", FieldValue.increment(1))

        database.collection("film").document(filmId)
            .update("likedBy", FieldValue.arrayUnion(username))
    }

    fun removeLikeFromFilmById(filmId: String, username: String) {
        database.collection("film").document(filmId)
            .update("likes", FieldValue.increment(-1))

        database.collection("film").document(filmId)
            .update("likedBy", FieldValue.arrayRemove(username))
    }

    fun addDislikeToFilmById(filmId: String, username: String){
        database.collection("film").document(filmId)
            .update("dislikes", FieldValue.increment(1))

        database.collection("film").document(filmId)
            .update("dislikedBy", FieldValue.arrayUnion(username))
    }

    fun removeDislikeFromFilmById(filmId: String, username: String) {
        database.collection("film").document(filmId)
            .update("dislikes", FieldValue.increment(-1))

        database.collection("film").document(filmId)
            .update("dislikedBy", FieldValue.arrayRemove(username))
    }

    fun getAllFilms(callback: (List<Film>) -> Unit) {
        database.collection("film")
            .get()
            .addOnSuccessListener { result ->
                val films = result.documents.mapNotNull { document ->
                    val film = document.toObject(Film::class.java)
                    film?.copy(key = document.id)
                }
                callback(films)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore Error", "Error getting films", exception)
                callback(emptyList()) // Return empty list on failure
            }
    }

    fun addReview(filmKey: String, review: Review) {
        database.collection("film").document(filmKey).collection("review").document().set(review)
    }

    fun getReviewsByFilmKey(filmKey: String, callback: (List<Review>) -> Unit) {
        database.collection("film").document(filmKey)
            .collection("review")
            .get()
            .addOnSuccessListener { result ->
                val reviews = result.documents.mapNotNull { document ->
                    document.toObject(Review::class.java)
                }
                callback(reviews)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore Error", "Error getting reviews", exception)
                callback(emptyList())
            }
    }

    fun addFilmWatchedByUser(username: String, filmKey: String, filmWatchedByUser: FilmWatchedByUser){
        database.collection("user").document(username).collection("filmWatched").document(filmKey).set(filmWatchedByUser)
    }
}