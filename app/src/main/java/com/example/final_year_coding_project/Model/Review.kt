package com.example.final_year_coding_project.Model

import java.util.Date
import java.util.concurrent.TimeUnit

data class Review(
    private val username: String = "",
    private val body: String = "",
    private val date: Date = Date()
)
{
    fun getUsername(): String {
        return this.username
    }

    fun getBody(): String {
        return this.body
    }

    fun getDate(): Date {
        return this.date
    }

    fun calculateTimeFromReview(): String {
        val now = Date()
        val diffInMillis = now.time - date.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        val weeks = days / 7
        val years = weeks / 52

        return when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "${minutes}m"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            weeks < 52 -> "${weeks}w"
            else -> "${years}y"
        }
    }
}