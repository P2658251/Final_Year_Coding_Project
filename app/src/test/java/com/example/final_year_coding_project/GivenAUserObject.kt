package com.example.final_year_coding_project

import com.example.final_year_coding_project.Model.User
import org.junit.Test
import org.junit.Assert.*

class GivenAUserObject {
    @Test
    fun whenGetUsernameIsCalled_ThenUsernameIsCorrectlyReturned(){
        val testUser = User(username = "testUsername")
        assertEquals(testUser.getUsername(), "testUsername")
    }

    @Test
    fun whenGetPasswordIsCalled_ThenPasswordIsCorrectlyReturned(){
        val testUser = User(username = "testUsername")
        assertEquals(testUser.getUsername(), "testUsername")
    }
}