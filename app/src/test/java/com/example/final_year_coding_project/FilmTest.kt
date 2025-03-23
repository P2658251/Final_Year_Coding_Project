package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*

class FilmTest {
    @Test
    fun getName() {
        var film = Film("Test film")

        assertEquals(film.getName(), "Test film")
    }

    @Test
    fun getReleaseDate() {
        var film = Film("Test film", "2020")

        assertEquals(film.getReleaseDate(), "2020")
    }

}