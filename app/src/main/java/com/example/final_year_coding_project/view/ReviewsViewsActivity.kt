package com.example.final_year_coding_project.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.Review
import com.example.final_year_coding_project.viewModel.ReviewsViewModel

class ReviewsViewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmKey = intent.getStringExtra("film_key") ?: ""
        val username = intent.getStringExtra("user_username") ?: ""
        val database = Database()

        setContent {
            ReviewsScreen(filmKey, username, this, ReviewsViewModel(database))
        }
    }

    @Composable
    private fun ReviewsScreen(
        filmKey: String,
        username: String,
        currentActivity: ReviewsViewsActivity,
        reviewsViewModel: ReviewsViewModel
    ) {
        val reviews = reviewsViewModel.reviews
        LaunchedEffect(Unit) {
            reviewsViewModel.loadReviews(filmKey)
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
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        reviewsViewModel.gotToFilmViewActivity(username, filmKey, currentActivity)
                    },
                    modifier = Modifier.Companion.weight(1f),
                ) {
                    Text(
                        text = "< Back",
                        color = Color.Companion.Black
                    )
                }

                Box(
                    modifier = Modifier.Companion.weight(1f),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Text(
                        text = "Reviews",
                        color = Color.Companion.White,
                        fontWeight = FontWeight.Companion.SemiBold,
                        fontSize = 30.sp
                    )
                }

                // Spacer to balance the layout visually
                Spacer(modifier = Modifier.Companion.weight(1f))
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
        Card(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(6.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier.Companion.padding(10.dp),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Column(modifier = Modifier.Companion.padding(16.dp)) {
                    Row {
                        Text(
                            text = review.getUsername(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Companion.SemiBold
                        )
                        Text(
                            text = review.calculateTimeFromReview(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Companion.SemiBold,
                            textAlign = TextAlign.Companion.End,
                            modifier = Modifier.Companion.weight(1f)
                        )
                    }
                    Text(
                        text = review.getBody(),
                        fontSize = 14.sp,
                        color = Color.Companion.Gray
                    )
                }
            }
        }
    }
}