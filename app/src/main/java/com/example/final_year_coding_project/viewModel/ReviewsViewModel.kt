package com.example.final_year_coding_project.viewModel

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.FilmViewActivity
import com.example.final_year_coding_project.ReviewsViewsActivity
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.Review

class ReviewsViewModel(private val database: Database): ViewModel() {
    var reviews by mutableStateOf<List<Review>>(emptyList())
        private set

    fun loadReviews(filmKey: String){
        database.getReviewsByFilmKey(filmKey) {fetchedReviews ->
            reviews = fetchedReviews
        }
    }

    fun gotToFilmViewActivity(
        username: String,
        filmKey: String,
        currentActivity: ReviewsViewsActivity
    ){
        val intent = Intent(currentActivity, FilmViewActivity::class.java)
        intent.putExtra("user_username", username)
        intent.putExtra("film_key", filmKey)
        currentActivity.startActivity(intent)
    }
}