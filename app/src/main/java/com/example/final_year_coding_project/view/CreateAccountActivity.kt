package com.example.final_year_coding_project.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.final_year_coding_project.model.Database
import com.example.final_year_coding_project.viewModel.CreateAccountViewModel

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Database()
        setContent {
            CreateAccountScreen(this, CreateAccountViewModel(database))
        }
    }
}

@Composable
fun CreateAccountScreen(
    activity: CreateAccountActivity,
    createAccountViewModel: CreateAccountViewModel
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/77aHwg1SCy89rfvQtiruPU58qEV.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Please enter your details", modifier = Modifier.padding(top = 10.dp))
        TextField(
            value = createAccountViewModel.usernameInput,
            onValueChange = { createAccountViewModel.updateUsername(it)},
            label = { Text(text = "Please enter a username", color = Color.Black) },
            modifier = Modifier.padding(top = 10.dp)
        )
        createAccountViewModel.updatePassword(composePasswordTextField("Please enter a password"))
        Button(onClick = {
            createAccountViewModel.createAccount(activity) },
            modifier = Modifier.padding(top = 10.dp)) {
            Text(text = "Create Account", color = Color.White)
        }
        Text(text = createAccountViewModel.errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        Text(text = "Already have an account?", modifier = Modifier.padding(top = 10.dp))
        TextButton(onClick = { createAccountViewModel.goToLoginPage(activity) }) {
            Text(text = "Log in", textDecoration = TextDecoration.Underline)
        }
    }
}