package com.example.final_year_coding_project.view

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
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.Film
import com.example.final_year_coding_project.viewModel.FilmsViewsModel

class FilmsViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("user_username") ?: ""
        val database = Database()
        setContent {
            FilmsViewsScreen(this, username, FilmsViewsModel(database))
        }
    }
}

@Composable
private fun FilmsViewsScreen(activity: FilmsViewsActivity, username: String, filmsViewsModel: FilmsViewsModel) {

    val films = filmsViewsModel.films
    var searchQuery by remember { mutableStateOf("") }
    val filteredFilms = films.filter { film ->
        film.getName().contains(searchQuery, ignoreCase = true) or film.getDirector().contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        filmsViewsModel.loadFilms()
    }

    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier.Companion
                .background(Color.Companion.DarkGray)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                Text(
                    text = "Films",
                    modifier = Modifier.Companion.padding(top = 20.dp),
                    color = Color.Companion.White,
                    fontWeight = FontWeight.Companion.SemiBold,
                    fontSize = 30.sp
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search films...", color = Color.Companion.White) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.Companion.padding(bottom = 16.dp)
                )
            }
        }
        LazyColumn {
            items(filteredFilms) { film ->
                FilmItem(film, username, activity, filmsViewsModel)
            }
        }
    }
}

@Composable
private fun FilmItem(
    film: Film,
    username: String,
    activity: FilmsViewsActivity,
    filmsViewsModel: FilmsViewsModel
){
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(6.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = { filmsViewsModel.goToFilmView(film, username, activity) }) {
        Row(
            modifier = Modifier.Companion.padding(10.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            AsyncImage(
                model = film.getPosterImage(),
                contentDescription = null,
                modifier = Modifier.Companion
                    .size(80.dp)
            )
            Column(modifier = Modifier.Companion.padding(16.dp)) {
                Text(
                    text = "${film.getName()} (${film.getReleaseDate()})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Companion.SemiBold
                )
                Text(
                    text = "Directed by: ${film.getDirector()}",
                    fontSize = 14.sp,
                    color = Color.Companion.Gray
                )
            }
        }
    }
}