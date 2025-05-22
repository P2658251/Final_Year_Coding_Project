package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.final_year_coding_project.Model.Database
import com.example.final_year_coding_project.Model.Review

class ReviewsViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmKey = intent.getStringExtra("film_key") ?: ""
        val username = intent.getStringExtra("user_username") ?: ""

        setContent {
            ReviewsScreen(filmKey, username, this)
        }
    }

    @Composable
    private fun ReviewsScreen(filmKey: String, username: String, currentActivity: ReviewsViewsActivity) {
        val database = Database()
        val reviews = remember { mutableStateListOf<Review>() }

        LaunchedEffect(Unit) {
            database.getReviewsByFilmKey(filmKey) { fetchedFilms ->
                reviews.clear()
                reviews.addAll(fetchedFilms)
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        val intent = Intent(currentActivity, FilmViewActivity::class.java)
                        intent.putExtra("user_username", username)
                        intent.putExtra("film_key", filmKey)
                        currentActivity.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "< Back",
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Reviews",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp
                    )
                }

                // Spacer to balance the layout visually
                Spacer(modifier = Modifier.weight(1f))
            }
            LazyColumn {
                items(reviews) { review ->
                    ReviewItem(review)
                }
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