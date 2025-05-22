package com.example.final_year_coding_project.Model

import java.util.Date

data class FilmWatchedByUser (
    private val name: String = "",
    private val dateWatched: Date = Date()
){
    fun getName(): String {
        return this.name
    }

    fun getDateWatched(): Date {
        return this.dateWatched
    }
}