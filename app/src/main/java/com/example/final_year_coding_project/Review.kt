package com.example.final_year_coding_project

import java.util.Date

class Review(
    private val username: String = "",
    private val reviewBody: String = "",
    private val date: Date = Date()
)
{
    fun getUsername(): String {
        return this.username
    }

    fun getReviewBody(): String {
        return this.reviewBody
    }

    fun getReviewDate(): Date {
        return this.date
    }
}