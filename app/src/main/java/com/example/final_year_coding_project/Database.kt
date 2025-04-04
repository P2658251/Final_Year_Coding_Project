package com.example.final_year_coding_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Database {
    private val database: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

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

    fun addUser(user: User){
        database.collection("user").document(user.username).set(user)
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

    fun getAllFilms(callback: (List<Film>) -> Unit) {
        database.collection("film")
            .get()
            .addOnSuccessListener { result ->
                val films = result.documents.mapNotNull { document ->
                    val film = document.toObject(Film::class.java)
                    film?.copy(id = document.id)
                }
                callback(films)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error getting films", exception)
                callback(emptyList()) // Return empty list on failure
            }
    }

//    fun addFilmToDatabase() {
//        val film = Film(
//            name = "Anora",
//            releaseDate = "2024",
//            director = "Sean Baker",
//            posterImage = "https://image.tmdb.org/t/p/w500/7MrgIUeq0DD2iF7GR6wqJfYZNeC.jpg",
//            description = "A young sex worker from Brooklyn gets her chance at a Cinderella story when she meets and impulsively marries the son of an oligarch. Once the news reaches Russia, her fairytale is threatened as his parents set out to get the marriage annulled.",
//            tagline = "Love is a hustle."
//        )
//        database.collection("film").document().set(film)
//    }
}