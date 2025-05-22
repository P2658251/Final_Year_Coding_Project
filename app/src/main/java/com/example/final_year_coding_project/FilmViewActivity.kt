package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.final_year_coding_project.Model.Database
import com.example.final_year_coding_project.Model.FilmWatchedByUser
import com.example.final_year_coding_project.Model.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FilmViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmKey = intent.getStringExtra("film_key") ?: ""
        val username = intent.getStringExtra("user_username") ?: ""
        val database = Database()
        setContent {
            FilmScreen(filmKey = filmKey, username, this, FilmViewModel(database), ReviewViewModel(database))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmScreen(
    filmKey: String,
    username: String,
    currentActivity: FilmViewActivity,
    filmViewModel: FilmViewModel,
    reviewViewModel: ReviewViewModel
) {
    val film = filmViewModel.film
    LaunchedEffect(Unit) {
        filmViewModel.loadFilm(filmKey, username)
    }

    val backgroundBrush = Brush.verticalGradient(listOf(Color.White, Color.DarkGray))
    Column(
        modifier = Modifier
            .background(backgroundBrush)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TextButton(
            onClick = {
                val intent = Intent(currentActivity, FilmsViewsActivity::class.java)
                intent.putExtra("user_username", username)
                currentActivity.startActivity(intent)
            },
            modifier = Modifier.align(alignment = Alignment.Start)
        ) {
            Text(
                text = "< Back",
                color = Color.Black
            )
        }
        ComposeFilmDetails(filmViewModel)
        ComposeLikeAndDislikeButtons(username, filmViewModel)
        ComposeRatingsRatioBar(filmViewModel)
        ComposeReviewSection(reviewViewModel, filmViewModel, username, currentActivity)
    }
}

fun goToReviewsViewsActivity(
    filmKey: String,
    username: String,
    currentActivity: FilmViewActivity
) {
    val intent = Intent(currentActivity, ReviewsViewsActivity::class.java)
    intent.putExtra("film_key", filmKey)
    intent.putExtra("user_username", username)
    currentActivity.startActivity(intent)
}

@Composable
private fun ComposeFilmDetails(filmViewModel: FilmViewModel) {
    var film = filmViewModel.film
    Row(modifier = Modifier.padding(20.dp)) {
        Text(
            text = film.getName(),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = film.getReleaseDate(),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    Text(text = "Directed by ${film.getDirector()}", color = Color.DarkGray, fontSize = 18.sp)
    AsyncImage(
        model = film.getPosterImage(),
        contentDescription = null,
        modifier = Modifier.clip(RoundedCornerShape(30.dp)),
    )
    Text(
        text = film.getTagline(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(10.dp)
    )
    Text(
        text = film.getDescription(),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
private fun ComposeLikeAndDislikeButtons(
    username: String,
    filmViewModel: FilmViewModel
){
    var film = filmViewModel.film
    var hasLiked = filmViewModel.hasLiked
    var hasDisliked = filmViewModel.hasDisliked

    Row {
        Text(
            text = film.getLikes().toString(),
            textAlign = TextAlign.Start,
            color = Color.Green,
            modifier = Modifier
                .weight(0.5f)
                .offset(20.dp)
        )

        val colourOfLikeButton = if (!hasLiked) Color.DarkGray else Color.Green
        Button(
            onClick = {
                filmViewModel.likeFilm(username)
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfLikeButton),
            modifier = Modifier.offset((-50).dp)
        ) {
            Text("👍")
        }

        val colourOfDislikeButton = if (!hasDisliked) Color.DarkGray else Color.Red
        Button(
            onClick = {
                filmViewModel.dislikeFilm(username)
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfDislikeButton),
            modifier = Modifier.offset(50.dp)
        ) {
            Text("👎")
        }
        Text(
            text = film.getDislikes().toString(),
            textAlign = TextAlign.End,
            color = Color.Red,
            modifier = Modifier
                .weight(0.5f)
                .offset(-20.dp)
        )
    }
}

@Composable
private fun ComposeRatingsRatioBar(filmViewModel: FilmViewModel) {
    var film = filmViewModel.film
    val colourOfRatingBar =
        if (film.getLikes() == 0 && film.getDislikes() == 0) Color.DarkGray else Color.Green
    val trackColourOfRatingBar =
        if (film.getLikes() == 0 && film.getDislikes() == 0) Color.DarkGray else Color.Red
    LinearProgressIndicator(
        progress = { film.calculateLikesToDislikesRatio() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        color = colourOfRatingBar,
        trackColor = trackColourOfRatingBar,
    )
}

@Composable
private fun ComposeReviewSection(
    reviewViewModel: ReviewViewModel,
    filmViewModel: FilmViewModel,
    username: String,
    currentActivity: FilmViewActivity
) {
    var film = filmViewModel.film
    var isUserLeavingReview by remember { mutableStateOf(false) }
    var isUserSettingDateWatched by remember { mutableStateOf(false) }
    Button(
        onClick = { isUserLeavingReview = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Text(text = "Leave a review", color = Color.White)
    }
    if (isUserLeavingReview) {
        Dialog(onDismissRequest = { isUserLeavingReview = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(375.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Start)
                    .clickable(onClick = { isUserSettingDateWatched = true })
                    .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    ){
                    val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(reviewViewModel.dateWatched))
                    Text("Date watched: $formattedDate")
                    if (isUserSettingDateWatched){
                        DatePickerModal(
                            onDateSelected = {
                                reviewViewModel.updateDateWatched(it)
                                isUserSettingDateWatched = false
                            },
                            onDismiss = { isUserSettingDateWatched = false }
                        )
                    }
                }
                TextField(
                    value = reviewViewModel.reviewInput,
                    onValueChange = { reviewViewModel.updateReviewInput(it) },
                    label = { Text("Leave a review") },
                    modifier = Modifier.height(275.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.End),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { isUserLeavingReview = false },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            reviewViewModel.saveReview(film, username) {
                                isUserLeavingReview = false
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
    if (reviewViewModel.showErrorDialog){
        AlertDialog(
            onDismissRequest = { reviewViewModel.showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { reviewViewModel.showErrorDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Error adding review") },
            text = { Text(reviewViewModel.errorMessage) }
        )
    }
    Button(
        onClick = { goToReviewsViewsActivity(film.getKey(), username, currentActivity)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Text(text = "View Reviews", color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}
