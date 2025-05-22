package com.example.final_year_coding_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.Model.Database
import com.example.final_year_coding_project.Model.Film
import com.example.final_year_coding_project.Model.FilmWatchedByUser
import com.example.final_year_coding_project.Model.Review
import java.util.Date

class ReviewViewModel(private val database: Database) : ViewModel() {

    var reviewInput by mutableStateOf("")
    var dateWatched by mutableStateOf(System.currentTimeMillis())
    var showErrorDialog by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun updateReviewInput(newInput: String) {
        reviewInput = newInput
    }

    fun updateDateWatched(newDate: Long?) {
        newDate?.let { dateWatched = it }
    }

    fun saveReview(
        film: Film,
        username: String,
        onSuccess: () -> Unit
    ) {
        val dateValidation = Validate.watchedDate(dateWatched)
        val reviewValidation = Validate.reviewBody(reviewInput.trim())

        if (dateValidation.getIsValidated() && reviewValidation.getIsValidated()) {
            database.addReview(
                film.getKey(),
                Review(username = username, body = reviewInput.trim())
            )
            database.addFilmWatchedByUser(
                username,
                film.getKey(),
                FilmWatchedByUser(film.getName(), Date(dateWatched))
            )
            onSuccess()
        } else {
            errorMessage = dateValidation.takeIf { !it.getIsValidated() }?.getErrorMessage()
                ?: reviewValidation.getErrorMessage()
            showErrorDialog = true
        }
    }
}
