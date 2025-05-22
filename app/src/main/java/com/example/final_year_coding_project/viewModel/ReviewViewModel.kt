package com.example.final_year_coding_project.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.model.Validate
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.Film
import com.example.final_year_coding_project.model.FilmWatchedByUser
import com.example.final_year_coding_project.model.Review
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
        val dateValidation = Validate.Companion.watchedDate(dateWatched)
        val reviewValidation = Validate.Companion.reviewBody(reviewInput.trim())

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