package com.example.final_year_coding_project.viewModel

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.CreateAccountActivity
import com.example.final_year_coding_project.UserLoginActivity
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.HashPassword
import com.example.final_year_coding_project.model.User
import com.example.final_year_coding_project.model.Validate

class CreateAccountViewModel(private val database: Database) : ViewModel() {
    var usernameInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    fun updateUsername(newUsername: String) {
        usernameInput = newUsername
    }

    fun updatePassword(newPassword: String) {
        passwordInput = newPassword
    }

    fun createAccount(activity: CreateAccountActivity) {
        val usernameValidation = Validate.username(usernameInput)
        val passwordValidation = Validate.password(passwordInput)

        if (usernameValidation.getIsValidated() && passwordValidation.getIsValidated()) {
            attemptToAddUser(usernameInput, passwordInput, activity)
        } else {
            errorMessage = listOfNotNull(
                usernameValidation.takeIf { !it.getIsValidated() }?.getErrorMessage(),
                passwordValidation.takeIf { !it.getIsValidated() }?.getErrorMessage()
            ).joinToString("\n")
        }
    }

    fun goToLoginPage(activity: CreateAccountActivity) {
        val intent = Intent(activity, UserLoginActivity::class.java)
        activity.startActivity(intent)
    }

    private fun attemptToAddUser(usernameInput: String, passwordInput: String, activity: CreateAccountActivity) {
        val hashedPassword = HashPassword.hash(passwordInput)
        database.addUser(User(usernameInput, hashedPassword))

        goToLoginPage(activity)
    }

}