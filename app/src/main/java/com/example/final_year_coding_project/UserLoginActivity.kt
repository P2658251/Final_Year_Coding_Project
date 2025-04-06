package com.example.final_year_coding_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.Button
import androidx.compose.material.SecureTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class UserLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserLoginScreen(this)
        }
    }
}

@Composable
private fun UserLoginScreen(activity: UserLoginActivity) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/cq9N64ucEtfIc3eMxNr1VzY9LH9.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = "Please Login to your account", modifier = Modifier.padding(top = 5.dp), fontWeight = FontWeight.SemiBold)
            var usernameInput by remember { mutableStateOf("") }
            TextField(
                value = usernameInput,
                onValueChange = { usernameInput = it },
                label = { Text("Enter your username") },
                modifier = Modifier.padding(top = 5.dp)
            )
            var passwordInput by remember { mutableStateOf("") }
            passwordInput = composePasswordTextField("Enter your password")
            val database = Database()
            val user = database.getUserByKey(usernameInput).observeAsState(initial = User()).value
            Button(onClick = {login(activity, passwordInput, user)}) {
                Text(text = "Login", color = Color.White)
            }
            Text(text = "Don't have an account?", modifier = Modifier.padding(top = 10.dp))
            TextButton(onClick = {goToCreateAccountActivity(activity)}) {
                Text(text = "Create one", textDecoration = TextDecoration.Underline)
            }
        }
    }
}

@Composable
fun composePasswordTextField(textFieldLabel: String): String {
    var passwordTextFieldState = remember { TextFieldState() }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    SecureTextField(
        state = passwordTextFieldState,
        label = { Text(textFieldLabel) },
        textObfuscationMode =
            if (passwordHidden) TextObfuscationMode.RevealLastTyped
            else TextObfuscationMode.Visible,
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = visibilityIcon, contentDescription = null)
            }
        },
        modifier = Modifier.padding(5.dp)
    )

    var passwordInput = passwordTextFieldState.text.toString()
    return passwordInput
}

private fun login(activity: UserLoginActivity, passwordInput: String, user: User) {
    if (passwordInput == user.getPassword()){
        val intent = Intent(activity, FilmViewsActivity::class.java)
        activity.startActivity(intent)
    }
}

private fun goToCreateAccountActivity(activity: UserLoginActivity) {
    val intent = Intent(activity, CreateAccountActivity::class.java)
    activity.startActivity(intent)
}
