package com.example.final_year_coding_project

data class Film (
    private val name: String = "",
    private val releaseDate: String = ""
)
{
     fun getName(): String {
        return this.name
    }

    fun getReleaseDate(): String{
        return this.releaseDate
    }
}
