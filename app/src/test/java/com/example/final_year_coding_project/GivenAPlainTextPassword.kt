package com.example.final_year_coding_project
import org.junit.Test
import org.junit.Assert.*

class GivenAPlainTextPassword {

    @Test
    fun whenThePasswordIsHashed_ThenTheHashedPasswordIsDifferentToThePlainTextOne(){
        val testPassword = "test password"

        assertNotEquals(testPassword, HashPassword.hash(testPassword))
    }

    @Test
    fun whenThePasswordIsHashed_ThenTheHashedPasswordIsDifferentToTheHashOfADifferentPassword(){
        val testPassword = "test password"
        val differentPassword = "different password"

        assertNotEquals(HashPassword.hash(differentPassword), HashPassword.hash(testPassword))
    }

    @Test
    fun whenThePasswordIsComparedToTheHashedOne_ThenTrueIsReturned(){
        val testPassword = "test password"
        val hashedPassword = HashPassword.hash(testPassword)

        assertTrue(HashPassword.checkPassword(testPassword, hashedPassword))
    }

    @Test
    fun whenThePasswordIsComparedToADifferentHashedOne_ThenFalseIsReturned(){
        val testPassword = "test password"
        val hashedPassword = HashPassword.hash("different password")

        assertFalse(HashPassword.checkPassword(testPassword, hashedPassword))
    }

}