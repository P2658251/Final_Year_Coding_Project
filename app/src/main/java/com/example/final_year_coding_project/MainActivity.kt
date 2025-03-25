package com.example.final_year_coding_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val film by database.getFilmById(filmId).observeAsState(initial = Film())

    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = film.getName(), fontWeight = FontWeight.SemiBold, fontSize = 30.sp, textAlign = TextAlign.Start)
            Text(text = film.getReleaseDate(), textAlign = TextAlign.End, modifier = Modifier.weight(1f))
        }
        Text(text = "Directed by ${film.getDirector()}", color = Color.DarkGray, fontSize = 18.sp)
        AsyncImage(
            model = film.getPosterImage(),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(30.dp)),
        )
        LinearProgressIndicator(
            progress = film.calculateLikesToDislikesRatio(),
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            color = Color.Green,
            trackColor = Color.Red
        )
    }
}
