package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*

class GivenAnAttemptToValidateUsername {

    @Test
    fun whenTheUsernameIsBelowTheExpectedLength_ThenReturnFalse_AndTheCorrectErrorMessage(){
        var username = "12"
        var validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")

        username = "1"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")

        username = ""
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")
    }

    @Test
    fun whenTheUsernameIsAboveTheExpectedLength_ThenReturnFalse_AndTheCorrectErrorMessage(){
        var username = "123456789123456789123"
        var validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")

        username = "1234567891234567891234"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")

        username = "12345678912345678912345"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username has to be at least 3 characters and at most 20 characters")
    }

    @Test
    fun whenTheUsernameIsTheExpectedLength_ThenReturnTrue(){
        var username = "123"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "1234"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "12345"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "1234567891234"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "12345678912345"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "123456789123456"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "12345678912345678912"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "1234567891234567891"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "123456789123456789"
        assertTrue(Validate.username(username = username).getIsValidated())

    }

    @Test
    fun whenTheUsernameHasInvalidCharactersInIt_ThenReturnFalse_AndTheCorrectErrorMessage(){
        var username = "@@@"
        var validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "???"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "///"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "%%%"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "!!!"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "£££"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "^^^"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "***"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "((("
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = ")))"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = ":::"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = ";;;"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "{{{"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "}}}"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "[[["
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "]]]"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "|||"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "\\\\\\"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "\"\"\""
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "+++"
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

        username = "==="
        validationResponse = Validate.username(username = username)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Username should only contain letters, numbers, fullstops, hyphens, and dashes")
    }

    @Test
    fun whenTheUsernameIsOnlyValidCharacters_ThenReturnTrue(){
        var username = "111"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "aaa"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "AAA"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "___"
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "..."
        assertTrue(Validate.username(username = username).getIsValidated())

        username = "---"
        assertTrue(Validate.username(username = username).getIsValidated())

    }
}