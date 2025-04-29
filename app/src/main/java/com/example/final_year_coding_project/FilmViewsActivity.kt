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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

class FilmViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("user_username") ?: ""

        setContent {
            FilmViewsScreen(this, username)
        }
    }
}

@Composable
private fun FilmViewsScreen(activity: FilmViewsActivity, username: String) {
    val database = Database()
    val films = remember { mutableStateListOf<Film>() }
    var searchQuery by remember { mutableStateOf("") }
    val filteredFilms = films.filter { film ->
        film.getName().contains(searchQuery, ignoreCase = true)
    }

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
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Films",
                    modifier = Modifier.padding(top = 20.dp),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search films...", color = Color.White) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
        LazyColumn {
            items(filteredFilms) {film ->
                FilmItem(film, username, activity)
            }
        }
    }
}

@Composable
private fun FilmItem(film: Film, username: String, activity: FilmViewsActivity){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = { goToFilmView(film, username, activity) }) {
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

private fun goToFilmView(
    film: Film,
    username: String,
    activity: FilmViewsActivity
) {
    val intent = Intent(activity, MainActivity::class.java)
    intent.putExtra("film_id", film.getId())
    intent.putExtra("user_username", username)
    activity.startActivity(intent)
}
