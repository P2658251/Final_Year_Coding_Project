package com.example.final_year_coding_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ReviewsViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filmKey = intent.getStringExtra("film_key") ?: ""

        setContent {
            ReviewsScreen(filmKey)
        }
    }

    @Composable
    private fun ReviewsScreen(filmKey: String) {
        val database = Database()
        val reviews = remember { mutableStateListOf<Review>() }

        LaunchedEffect(Unit) {
            database.getReviewsByFilmKey(filmKey) { fetchedFilms ->
                reviews.clear()
                reviews.addAll(fetchedFilms)
            }
        }

        LazyColumn {
            items(reviews) {review ->
                ReviewItem(review)
            }
        }
    }

    @Composable
    private fun ReviewItem(review: Review){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
            Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text(
                            text = review.getUsername(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = review.calculateTimeFromReview(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Text(
                        text = review.getBody(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}