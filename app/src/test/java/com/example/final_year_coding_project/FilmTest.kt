package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*

class FilmTest {
    @Test
    fun getName() {
        var film = Film(name = "Test film")

        assertEquals(film.getName(), "Test film")
    }

    @Test
    fun getReleaseDate() {
        var film = Film(name = "Test film", releaseDate = "2020")

        assertEquals(film.getReleaseDate(), "2020")
    }

    @Test
    fun getDirector() {
        var film = Film(name = "Test film", releaseDate = "2020", director = "Test Director")

        assertEquals(film.getDirector(), "Test Director")
    }

    @Test
    fun getPoster() {
        var film = Film(
            name = "Test film",
            releaseDate = "2020",
            director = "Test Director",
            posterImage = "Test Poster"
        )

        assertEquals(film.getPosterImage(), "Test Poster")
    }

}