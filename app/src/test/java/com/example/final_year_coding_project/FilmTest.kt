package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*
import kotlin.random.Random

class FilmTest {

    @Test
    fun setId(){
        var film = Film(id = "wrong id")

        film.setId("correct id")

        assertEquals("correct id", film.getId())
    }

    @Test
    fun getName() {
        var film = Film(name = "Test film")

        assertEquals(film.getName(), "Test film")
    }

    @Test
    fun getReleaseDate() {
        var film = Film(releaseDate = "2020")

        assertEquals(film.getReleaseDate(), "2020")
    }

    @Test
    fun getDirector() {
        var film = Film(director = "Test Director")

        assertEquals(film.getDirector(), "Test Director")
    }

    @Test
    fun getId(){
        var film = Film(id = "Test Id")

        assertEquals("Test Id", film.getId())
    }

    @Test
    fun getLikes(){
        var randomTestInteger = Random.nextInt(0, 100)
        var film = Film(likes = randomTestInteger)

        assertEquals(randomTestInteger, film.getLikes())
    }

    @Test
    fun getDislikes(){
        var randomTestInteger = Random.nextInt(0, 100)
        var film = Film(dislikes = randomTestInteger)

        assertEquals(randomTestInteger, film.getDislikes())
    }

    @Test
    fun getDescription(){
        var film = Film(description = "Test Description")

        assertEquals("Test Description", film.getDescription())
    }

    @Test
    fun getPoster() {
        var film = Film(posterImage = "Test Poster")

        assertEquals(film.getPosterImage(), "Test Poster")
    }

}