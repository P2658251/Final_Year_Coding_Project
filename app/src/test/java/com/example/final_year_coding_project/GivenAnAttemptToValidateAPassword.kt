package com.example.final_year_coding_project

import org.junit.Test
import org.junit.Assert.*

class GivenAnAttemptToValidateAPassword {

    @Test
    fun whenThePasswordIsBelowTheExpectedLength_ThenReturnFalse_AndTheCorrectErrorMessage(){
        var password = ""
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "1"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "12"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "123456"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "1234567"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")
    }

    @Test
    fun whenThePasswordIsAboveTheExpectedLength_ThenReturnFalse_AndTheCorrectErrorMessage(){
        var password = "123456789123456789123"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "1234567"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")

        password = "1234567"
        validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has to be at least 8 characters and at most 20 characters")
    }

    @Test
    fun whenThePasswordIsTheExpectedLength_AndContainsTheNecessaryCharacters_ThenReturnTrue(){
        var password = "Pp6!Pp6!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "Pp6!Pp6!1"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "Pp6!Pp6!12"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "1234567891Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "12345678912Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "123456789123Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "12345678912345678Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "1234567891234567Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

        password = "123456789123456Pp!"
        assertTrue(Validate.password(password = password).getIsValidated())

    }

    @Test
    fun whenThePasswordDoesNotContainASpecialCharacter_ThenReturnFalse_AndTheExpectedErrorMessage(){
        var password = "TestPassword1"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has have at least one uppercase letter, at least lowercase letter, at least one digit and at least one special character")
    }

    @Test
    fun whenThePasswordDoesNotContainAnUppercaseCharacter_ThenReturnFalse_AndTheExpectedErrorMessage(){
        var password = "testpassword1!"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has have at least one uppercase letter, at least lowercase letter, at least one digit and at least one special character")
    }

    @Test
    fun whenThePasswordDoesNotContainALowercaseCharacter_ThenReturnFalse_AndTheExpectedErrorMessage(){
        var password = "TESTPASSWORD1!"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has have at least one uppercase letter, at least lowercase letter, at least one digit and at least one special character")
    }

    @Test
    fun whenThePasswordDoesNotContainADigit_ThenReturnFalse_AndTheExpectedErrorMessage(){
        var password = "TestPassword!"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password has have at least one uppercase letter, at least lowercase letter, at least one digit and at least one special character")
    }

    @Test
    fun whenThePasswordDoesContainASpace(){
        var password = "Test Password1!"
        var validationResponse = Validate.password(password = password)
        assertFalse(validationResponse.getIsValidated())
        assertEquals(validationResponse.getErrorMessage(), "Password should not have any spaces in it")
    }
}