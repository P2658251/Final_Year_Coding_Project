package com.example.final_year_coding_project

import org.mindrot.jbcrypt.BCrypt

class HashPassword {
    companion object {
        fun hash(plainTextPassword: String): String {
            return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt())
        }

        fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
            return BCrypt.checkpw(plainPassword, hashedPassword)
        }
    }
}