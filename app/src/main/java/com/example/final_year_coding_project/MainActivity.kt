package com.example.final_year_coding_project

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Database()
        val filmNameTextView = findViewById<TextView>(R.id.txtFilmName)
        val filmReleaseDateTextView = findViewById<TextView>(R.id.txtFilmReleaseDate)
        db.getFilmById("3M6BznipYGRycQVOZG2H") { film ->
            if (film != null) {
                filmNameTextView.text = film.getName()
                filmReleaseDateTextView.text = film.getReleaseDate()
            } else {
                filmNameTextView.text = "Film not found"
            }
        }

    }
}