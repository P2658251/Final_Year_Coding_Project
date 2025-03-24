package com.example.final_year_coding_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = film.getName(), fontWeight = FontWeight.SemiBold)
        Text(text = film.getReleaseDate(), color = Color.DarkGray, fontSize = 7.sp)
    }
}