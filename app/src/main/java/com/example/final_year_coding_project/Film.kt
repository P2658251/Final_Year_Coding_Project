package com.example.final_year_coding_project

data class Film (
    private val name: String = "",
    private val releaseDate: String = "",
    private val director: String = "",
    private val posterImage: String = ""
)
{
     fun getName(): String {
        return this.name
    }

    fun getReleaseDate(): String{
        return this.releaseDate
    }

    fun getDirector(): String{
        return this.director
    }

    fun getPosterImage(): String{
        return this.posterImage
    }
}
