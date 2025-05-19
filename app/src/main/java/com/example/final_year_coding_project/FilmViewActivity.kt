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
import androidx.compose.runtime.livedata.observeAsState
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FilmViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmKey = intent.getStringExtra("film_key") ?: ""
        val username = intent.getStringExtra("user_username") ?: ""
        setContent {
            FilmScreen(filmId = filmKey, username, this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmScreen(filmId: String, username: String, currentActivity: FilmViewActivity) {
    val database = Database()

    val filmLiveData = database.getFilmByKey(filmId).observeAsState(initial = Film())
    var film by remember { mutableStateOf(filmLiveData.value) }
    LaunchedEffect(filmLiveData.value) {
        film = filmLiveData.value
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
        ComposeFilmDetails(film)
        film = composeLikeAndDislikeButtons(film, username, database)
        ComposeRatingsRatioBar(film)
        ComposeReviewSection(database, film, username, currentActivity)
    }
}

fun goToReviewsViewsActivity(
    filmKey: String,
    currentActivity: FilmViewActivity
) {
    val intent = Intent(currentActivity, ReviewsViewsActivity::class.java)
    intent.putExtra("film_key", filmKey)
    currentActivity.startActivity(intent)
}

@Composable
private fun ComposeFilmDetails(film: Film) {
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
private fun composeLikeAndDislikeButtons(
    film: Film,
    username: String,
    database: Database
): Film {
    var film1 = film
    var hasLiked by remember { mutableStateOf(false) }
    hasLiked = film1.getLikedBy().contains(username)
    var hasDisliked by remember { mutableStateOf(false) }
    hasDisliked = film1.getDislikedBy().contains(username)

    Row {
        Text(
            text = film1.getLikes().toString(),
            textAlign = TextAlign.Start,
            color = Color.Green,
            modifier = Modifier
                .weight(0.5f)
                .offset(20.dp)
        )

        val colourOfLikeButton = if (!hasLiked) Color.DarkGray else Color.Green
        Button(
            onClick = {
                var newLikes = film1.getLikes()
                var newDislikes = film1.getDislikes()

                if (hasLiked) {
                    database.removeLikeFromFilmById(film1.getKey(), username)
                    newLikes -= 1

                    hasLiked = false
                } else if (hasDisliked) {
                    database.addLikeToFilmById(film1.getKey(), username)
                    newLikes += 1

                    database.removeDislikeFromFilmById(film1.getKey(), username)
                    newDislikes -= 1

                    hasLiked = true
                    hasDisliked = false
                } else {
                    database.addLikeToFilmById(film1.getKey(), username)
                    newLikes += 1

                    hasLiked = true
                }

                film1 = film1.copy(likes = newLikes, dislikes = newDislikes)
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfLikeButton),
            modifier = Modifier.offset((-50).dp)
        ) {
            Text("👍")
        }

        val colourOfDislikeButton = if (!hasDisliked) Color.DarkGray else Color.Red
        Button(
            onClick = {
                var newLikes = film1.getLikes()
                var newDislikes = film1.getDislikes()

                if (hasDisliked) {
                    database.removeDislikeFromFilmById(film1.getKey(), username)
                    newDislikes -= 1

                    hasDisliked = false
                } else if (hasLiked) {
                    database.addDislikeToFilmById(film1.getKey(), username)
                    newDislikes += 1

                    database.removeLikeFromFilmById(film1.getKey(), username)
                    newLikes -= 1

                    hasDisliked = true
                    hasLiked = false
                } else {
                    database.addDislikeToFilmById(film1.getKey(), username)
                    newDislikes += 1

                    hasDisliked = true
                }

                film1 = film1.copy(likes = newLikes, dislikes = newDislikes)
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfDislikeButton),
            modifier = Modifier.offset(50.dp)
        ) {
            Text("👎")
        }
        Text(
            text = film1.getDislikes().toString(),
            textAlign = TextAlign.End,
            color = Color.Red,
            modifier = Modifier
                .weight(0.5f)
                .offset(-20.dp)
        )
    }
    return film1
}

@Composable
private fun ComposeRatingsRatioBar(film: Film) {
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
    database: Database,
    film: Film,
    username: String,
    currentActivity: FilmViewActivity
) {
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
            var reviewInput by rememberSaveable { mutableStateOf("") }
            var dateWatched by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }
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
                    .clickable(onClick = {isUserSettingDateWatched = true})
                    .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    ){
                    var date = Date()
                    if (dateWatched != null) {
                        date = Date(dateWatched!!)
                    }
                    val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
                    Text("Date watched: $formattedDate")
                    if (isUserSettingDateWatched){
                        DatePickerModal(
                            onDateSelected = {
                                dateWatched = it
                                isUserSettingDateWatched = false
                            },
                            onDismiss = { isUserSettingDateWatched = false }
                        )
                    }
                }
                TextField(
                    value = reviewInput,
                    onValueChange = { reviewInput = it },
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
                            database.addReview(
                                film.getKey(),
                                Review(username = username, body = reviewInput)
                            )
                            database.addFilmWatchedByUser(username = username, filmKey = film.getKey(), filmWatchedByUser = FilmWatchedByUser(film.getName(), Date(dateWatched!!)))
                            isUserLeavingReview = false
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
    Button(
        onClick = { goToReviewsViewsActivity(film.getKey(), currentActivity)},
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
