package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil3.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmId = intent.getStringExtra("film_id") ?: ""
        val username = intent.getStringExtra("user_username") ?: ""
        setContent {
            FilmScreen(filmId = filmId, username, this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmScreen(filmId: String, username: String, activity: MainActivity) {
    val database = Database()

    val filmLiveData = database.getFilmById(filmId).observeAsState(initial = Film())
    // Create a MutableState to track changes manually
    var film by remember { mutableStateOf(filmLiveData.value) }
    // Observe changes from the database
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
                val intent = Intent(activity, FilmViewsActivity::class.java)
                intent.putExtra("user_username", username)
                activity.startActivity(intent)
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
    }
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
                    database.removeLikeFromFilmById(film1.getId(), username)
                    newLikes -= 1

                    hasLiked = false
                } else if (hasDisliked) {
                    database.addLikeToFilmById(film1.getId(), username)
                    newLikes += 1

                    database.removeDislikeFromFilmById(film1.getId(), username)
                    newDislikes -= 1

                    hasLiked = true
                    hasDisliked = false
                } else {
                    database.addLikeToFilmById(film1.getId(), username)
                    newLikes += 1

                    hasLiked = true
                }

                film1 = film1.copy(likes = newLikes, dislikes = newDislikes) // Single state update
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
                    database.removeDislikeFromFilmById(film1.getId(), username)
                    newDislikes -= 1

                    hasDisliked = false
                } else if (hasLiked) {
                    database.addDislikeToFilmById(film1.getId(), username)
                    newDislikes += 1

                    database.removeLikeFromFilmById(film1.getId(), username)
                    newLikes -= 1

                    hasDisliked = true
                    hasLiked = false
                } else {
                    database.addDislikeToFilmById(film1.getId(), username)
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
