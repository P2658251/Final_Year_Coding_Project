package com.example.final_year_coding_project

data class Film (
    private var key: String = "",
    private val name: String = "",
    private val tagline: String = "",
    private val releaseDate: String = "",
    private val director: String = "",
    private val posterImage: String = "",
    private var likes: Int = 0,
    private val dislikes: Int = 0,
    private val description: String = "",
    private val likedBy: List<String> = emptyList<String>(),
    private val dislikedBy: List<String> = emptyList<String>()
)
{
    fun getKey():String{
        return this.key
    }

    fun setKey(filmKey: String){
        key = filmKey
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

    fun getDescription(): String{
        return this.description
    }

    fun getTagline(): String {
        return this.tagline
    }

    fun getLikedBy(): List<String> {
        return this.likedBy
    }

    fun getDislikedBy(): List<String> {
        return this.dislikedBy
    }

    fun calculateLikesToDislikesRatio(): Float {
        val totalRatings = likes + dislikes

        if (totalRatings == 0)
            return 0.5f

        val ratingsRatio: Float = likes.toFloat() / totalRatings
        return ratingsRatio
    }
}
