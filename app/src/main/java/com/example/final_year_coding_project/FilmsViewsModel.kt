package com.example.final_year_coding_project

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.Model.Database
import com.example.final_year_coding_project.Model.Film

class FilmsViewsModel(private val database: Database) : ViewModel() {
    var films by mutableStateOf<List<Film>>(emptyList())
        private set

    fun loadFilms() {
        database.getAllFilms { fetchedFilms ->
            films = fetchedFilms
        }
    }

    fun goToFilmView(
        film: Film,
        username: String,
        activity: FilmsViewsActivity
    ) {
        val intent = Intent(activity, FilmViewActivity::class.java)
        intent.putExtra("film_key", film.getKey())
        intent.putExtra("user_username", username)
        activity.startActivity(intent)
    }

}
