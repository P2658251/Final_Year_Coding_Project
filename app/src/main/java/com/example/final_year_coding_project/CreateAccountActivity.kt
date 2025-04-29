package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreateAccountScreen(this)
        }
    }
}

@Composable
fun CreateAccountScreen(activity: CreateAccountActivity) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/77aHwg1SCy89rfvQtiruPU58qEV.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Please enter your details", modifier = Modifier.padding(top = 10.dp))
        var usernameInput by remember { mutableStateOf("") }
        TextField(
            value = usernameInput,
            onValueChange = { usernameInput = it },
            label = { Text(text = "Please enter a username", color = Color.Black) },
            modifier = Modifier.padding(top = 10.dp)
        )
        var passwordInput by remember { mutableStateOf("") }
        passwordInput = composePasswordTextField("Please enter a password")
        Button(onClick = { attemptToAddUser(usernameInput, passwordInput, activity) }, modifier = Modifier.padding(top = 10.dp)) {
            Text(text = "Create Account", color = Color.White)
        }
        Text(text = "Already have an account?", modifier = Modifier.padding(top = 10.dp))
        TextButton(onClick = { goToLoginPage(activity) }) {
            Text(text = "Log in", textDecoration = TextDecoration.Underline)
        }
    }
}

private fun attemptToAddUser(usernameInput: String, passwordInput: String, activity: CreateAccountActivity) {
    val database = Database()
    val hashedPassword = HashPassword.hash(passwordInput)
    database.addUser(User(usernameInput, hashedPassword))

    goToLoginPage(activity)
}

private fun goToLoginPage(activity: CreateAccountActivity) {
    val intent = Intent(activity, UserLoginActivity::class.java)
    activity.startActivity(intent)
}