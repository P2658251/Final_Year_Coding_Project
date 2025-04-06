package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*
import kotlin.random.Random

class GivenAFilmObject {

    @Test
    fun whenSetIdIsCalledWithAParameter_ThenTheIdGetsCorrectlySet(){
        var film = Film(id = "wrong id")

        film.setId("correct id")

        assertEquals("correct id", film.getId())
    }

    @Test
    fun whenGetNameIsCalled_ThenNameIsReturnedCorrectly() {
        var film = Film(name = "Test film")

        assertEquals(film.getName(), "Test film")
    }

    @Test
    fun whenGetReleaseDateIsCalled_ThenReleaseDateIsReturnedCorrectly() {
        var film = Film(releaseDate = "2020")

        assertEquals(film.getReleaseDate(), "2020")
    }

    @Test
    fun whenGetDirectorIsCalled_ThenDirectorIsReturnedCorrectly() {
        var film = Film(director = "Test Director")

        assertEquals(film.getDirector(), "Test Director")
    }

    @Test
    fun whenGetIdIsCalled_ThenIdIsReturnedCorrectly(){
        var film = Film(id = "Test Id")

        assertEquals("Test Id", film.getId())
    }

    @Test
    fun whenGetLikesIsCalled_ThenLikesAreReturnedCorrectly(){
        var randomTestInteger = Random.nextInt(0, 100)
        var film = Film(likes = randomTestInteger)

        assertEquals(randomTestInteger, film.getLikes())
    }

    @Test
    fun whenGetDislikesIsCalled_ThenDislikesAreReturnedCorrectly(){
        var randomTestInteger = Random.nextInt(0, 100)
        var film = Film(dislikes = randomTestInteger)

        assertEquals(randomTestInteger, film.getDislikes())
    }

    @Test
    fun whenGetDescriptionIsCalled_ThenDescriptionIsReturnedCorrectly(){
        var film = Film(description = "Test Description")

        assertEquals("Test Description", film.getDescription())
    }

    @Test
    fun whenGetPosterIsCalled_ThenPosterIsReturnedCorrectly() {
        var film = Film(posterImage = "Test Poster")

        assertEquals(film.getPosterImage(), "Test Poster")
    }

    @Test
    fun whenCalculateLikesToDislikesRatioIsCalled_AndTheFilmOnlyHasLikesAndNoDislikes_Then1IsReturnedCorrectly() {
        var film = Film(likes = 100, dislikes = 0)

        assertEquals(film.calculateLikesToDislikesRatio(), 1f)
    }

    @Test
    fun whenCalculateLikesToDislikesRatioIsCalled_AndTheFilmOnlyHasDislikesAndNoLikes_Then0IsReturnedCorrectly() {
        var film = Film(likes = 0, dislikes = 100)

        assertEquals(film.calculateLikesToDislikesRatio(), 0f)
    }

    @Test
    fun whenCalculateLikesToDislikesRatioIsCalled_AndTheFilmHasNoDislikesAndNoLikes_ThenHalfIsReturnedCorrectly() {
        var film = Film(likes = 0, dislikes = 0)

        assertEquals(film.calculateLikesToDislikesRatio(), 0.5f)
    }

    @Test
    fun whenCalculateLikesToDislikesRatioIsCalled_AndTheFilmHasEqualDislikesAndLikes_ThenHalfIsReturnedCorrectly() {
        var film = Film(likes = 50, dislikes = 50)

        assertEquals(film.calculateLikesToDislikesRatio(), 0.5f)
    }

}