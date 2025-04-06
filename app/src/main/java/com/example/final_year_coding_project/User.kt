package com.example.final_year_coding_project

class User(
    private val username: String = "",
    private val password: String = ""
) {
    fun getPassword(): String {
        return this.password
    }

    fun getUsername(): String {
        return this.username
    }
}