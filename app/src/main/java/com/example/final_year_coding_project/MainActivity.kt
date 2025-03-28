package com.example.final_year_coding_project

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
        setContent {
            FilmScreen(filmId = "3M6BznipYGRycQVOZG2H")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScreen(filmId: String) {
    val database = Database()

    val filmLiveData = database.getFilmById(filmId).observeAsState(initial = Film())
    // Create a MutableState to track changes manually
    var film by remember { mutableStateOf(filmLiveData.value) }
    // Observe changes from the database
    LaunchedEffect(filmLiveData.value) {
        film = filmLiveData.value
    }
    var hasLiked by remember { mutableStateOf(false) }
    var hasDisliked by remember { mutableStateOf(false) }

    val backgroundBrush = Brush.verticalGradient(listOf(Color.White, Color.DarkGray))
    Column(modifier = Modifier
        .background(backgroundBrush)
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally,) {
        Row (modifier = Modifier.padding(20.dp)){
            Text(text = film.getName(), fontWeight = FontWeight.Bold, fontSize = 30.sp, textAlign = TextAlign.Start)
            Text(text = film.getReleaseDate(), textAlign = TextAlign.End, modifier = Modifier.weight(1f))
        }
        Text(text = "Directed by ${film.getDirector()}", color = Color.DarkGray, fontSize = 18.sp)
        AsyncImage(
            model = film.getPosterImage(),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(30.dp)),
        )
        Text(text = film.getDescription(), textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
        Row{
            Text(text = film.getLikes().toString(), textAlign = TextAlign.Start, color = Color.Green, modifier = Modifier
                .weight(0.5f)
                .offset(20.dp)
            )

            val colourOfLikeButton = if (!hasLiked) Color.DarkGray else Color.Green
            Button(onClick = {
                var newLikes = film.getLikes()
                var newDislikes = film.getDislikes()

                if (hasLiked) {
                    database.removeLikeFromFilmById(film.getId())
                    newLikes -= 1

                    hasLiked = false
                } else if (hasDisliked) {
                    database.addLikeToFilmById(film.getId())
                    newLikes += 1

                    database.removeDislikeFromFilmById(film.getId())
                    newDislikes -= 1

                    hasLiked = true
                    hasDisliked = false
                } else {
                    database.addLikeToFilmById(film.getId())
                    newLikes += 1

                    hasLiked = true
                }

                film = film.copy(likes = newLikes, dislikes = newDislikes) // Single state update
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfLikeButton),
                modifier = Modifier.offset(-50.dp)
            ) {
                Text("👍")
            }

            val colourOfDislikeButton = if (!hasDisliked) Color.DarkGray else Color.Red
            Button(onClick = {
                var newLikes = film.getLikes()
                var newDislikes = film.getDislikes()

                if (hasDisliked) {
                    database.removeDislikeFromFilmById(film.getId())
                    newDislikes -= 1

                    hasDisliked = false
                } else if (hasLiked) {
                    database.addDislikeToFilmById(film.getId())
                    newDislikes += 1

                    database.removeLikeFromFilmById(film.getId())
                    newLikes -= 1

                    hasDisliked = true
                    hasLiked = false
                } else {
                    database.addDislikeToFilmById(film.getId())
                    newDislikes += 1

                    hasDisliked = true
                }

                film = film.copy(likes = newLikes, dislikes = newDislikes)
            }, colors = ButtonDefaults.buttonColors(containerColor = colourOfDislikeButton),
                modifier = Modifier.offset(50.dp)
            ) {
                Text("👎")
            }
            Text(text = film.getDislikes().toString(), textAlign = TextAlign.End, color = Color.Red, modifier = Modifier
                .weight(0.5f)
                .offset(-20.dp))
        }

        val colourOfRatingBar = if (film.getLikes() == 0 && film.getDislikes() == 0) Color.DarkGray else Color.Green
        val trackColourOfRatingBar = if (film.getLikes() == 0 && film.getDislikes() == 0) Color.DarkGray else Color.Red
        LinearProgressIndicator(
            progress = { film.calculateLikesToDislikesRatio() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            color = colourOfRatingBar,
            trackColor = trackColourOfRatingBar,
        )
    }
}
