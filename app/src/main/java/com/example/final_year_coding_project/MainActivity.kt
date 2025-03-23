package com.example.final_year_coding_project

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val docRef = db.collection("film").document("3M6BznipYGRycQVOZG2H")
        val filmNameTextView = findViewById<TextView>(R.id.txtFilmName)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            val film = documentSnapshot.toObject(Film::class.java)
            filmNameTextView.apply{
                text = film?.getName()
            }
        }

    }
}