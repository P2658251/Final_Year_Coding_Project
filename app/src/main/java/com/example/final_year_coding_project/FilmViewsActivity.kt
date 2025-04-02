package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil3.compose.AsyncImage

class FilmViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmViewsScreen(this)
        }
    }
}

@Composable
fun FilmViewsScreen(activity: FilmViewsActivity) {
    val database = Database()
    val films = remember { mutableStateListOf<Film>() }

    // Fetch films from the database
    LaunchedEffect(Unit) {
        database.getAllFilms { fetchedFilms ->
            films.clear()
            films.addAll(fetchedFilms)
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row (modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Films", modifier = Modifier.padding(20.dp), color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 30.sp)
        }
        LazyColumn {
            items(films) {film ->
                FilmItem(film, activity)
            }
        }
    }
}

@Composable
fun FilmItem(film: Film, activity: FilmViewsActivity){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = { goToFilmView(film, activity) }) {
        Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(
                model = film.getPosterImage(),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${film.getName()} (${film.getReleaseDate()})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Directed by: ${film.getDirector()}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun goToFilmView(
    film: Film,
    activity: FilmViewsActivity
) {
    val intent = Intent(activity, MainActivity::class.java)
    intent.putExtra("film_id", film.getId())
    activity.startActivity(intent)
}
