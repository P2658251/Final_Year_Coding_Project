package com.example.final_year_coding_project.viewModel


import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.final_year_coding_project.CreateAccountActivity
import com.example.final_year_coding_project.FilmsViewsActivity
import com.example.final_year_coding_project.UserLoginActivity
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.model.HashPassword
import com.example.final_year_coding_project.model.User

class UserLoginViewModel(private val database: Database = Database()) : ViewModel() {
    var usernameInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var user by mutableStateOf(User())

    fun updateUsername(newUsername: String) {
        usernameInput = newUsername
    }

    fun updatePassword(newPassword: String) {
        passwordInput = newPassword
    }

    fun loadUser(onUserLoaded: (User) -> Unit) {
        database.getUserByKey(usernameInput).observeForever { fetchedUser ->
            val safeUser = fetchedUser ?: User()
            user = safeUser
            onUserLoaded(safeUser)
        }
    }

    fun login(activity: UserLoginActivity) {
        loadUser{
            errorMessage = if (!canLogin(activity)) {
                "Username and Password do not match"
            } else {
                ""
            }
        }
        if (user.getUsername() == "")
            errorMessage = "Username and Password do not match"
    }

    fun canLogin(activity: UserLoginActivity): Boolean {
        if (user.getUsername() != "" && (HashPassword.checkPassword(passwordInput, user.getPassword()))){
            val intent = Intent(activity, FilmsViewsActivity::class.java)
            intent.putExtra("user_username", user.getUsername())
            activity.startActivity(intent)
            return true
        }

        return false
    }

   fun goToCreateAccountActivity(activity: UserLoginActivity) {
        val intent = Intent(activity, CreateAccountActivity::class.java)
        activity.startActivity(intent)
    }
}
