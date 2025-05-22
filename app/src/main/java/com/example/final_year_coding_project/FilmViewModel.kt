package com.example.final_year_coding_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.Model.Database
import com.example.final_year_coding_project.Model.Film

class FilmViewModel(private val database: Database) : ViewModel() {
    var film by mutableStateOf(Film())
    private set

    var hasLiked by mutableStateOf(false)
        private set

    var hasDisliked by mutableStateOf(false)
        private set

    fun loadFilm(filmKey: String, username : String) {
        database.getFilmByKey(filmKey).observeForever { fetchedFilm ->
            film = fetchedFilm
        }
        hasLiked = film.getLikedBy().contains(username)
        hasDisliked = film.getDislikedBy().contains(username)
    }

    fun likeFilm(username: String) {
        var newLikes = film.getLikes()
        var newDislikes = film.getDislikes()

        if (hasLiked) {
            database.removeLikeFromFilmById(film.getKey(), username)
            newLikes -= 1
            hasLiked = false
        } else if (hasDisliked) {
            database.addLikeToFilmById(film.getKey(), username)
            newLikes += 1
            database.removeDislikeFromFilmById(film.getKey(), username)
            newDislikes -= 1
            hasLiked = true
            hasDisliked = false
        } else {
            database.addLikeToFilmById(film.getKey(), username)
            newLikes += 1
            hasLiked = true
        }

        film = film.copy(likes = newLikes, dislikes = newDislikes)
    }

    fun dislikeFilm(username: String) {
        var newLikes = film.getLikes()
        var newDislikes = film.getDislikes()

        if (hasDisliked) {
            database.removeDislikeFromFilmById(film.getKey(), username)
            newDislikes -= 1
            hasDisliked = false
        } else if (hasLiked) {
            database.addDislikeToFilmById(film.getKey(), username)
            newDislikes += 1
            database.removeLikeFromFilmById(film.getKey(), username)
            newLikes -= 1
            hasDisliked = true
            hasLiked = false
        } else {
            database.addDislikeToFilmById(film.getKey(), username)
            newDislikes += 1
            hasDisliked = true
        }

        film = film.copy(likes = newLikes, dislikes = newDislikes)
    }
}