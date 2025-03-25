package com.example.final_year_coding_project

data class Film (
    private val id: String = "",
    private val name: String = "",
    private val releaseDate: String = "",
    private val director: String = "",
    private val posterImage: String = "",
    private val likes: Int = 0,
    private val dislikes: Int = 0,
)
{
    fun getId():String{
        return this.id
    }

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

    fun getLikes(): Int{
        return this.likes
    }

    fun getDislikes(): Int{
        return this.dislikes
    }

    fun calculateLikesToDislikesRatio(): Float {
        val totalRatings = likes + dislikes

        if (totalRatings == 0)
            return 0.5f

        val ratingsRatio: Float = likes.toFloat() / totalRatings
        return ratingsRatio
    }
}
