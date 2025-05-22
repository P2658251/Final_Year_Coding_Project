package com.example.final_year_coding_project.model

class User(
    private val username: String = "",
    private val password: String = "",
    private val filmsWatched: List<String> = emptyList<String>()
) {
    fun getPassword(): String {
        return this.password
    }

    fun getUsername(): String {
        return this.username
    }

    fun getFilmsWatched(): List<String> {
        return this.filmsWatched
    }
}