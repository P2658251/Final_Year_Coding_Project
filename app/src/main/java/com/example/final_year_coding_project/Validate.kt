package com.example.final_year_coding_project


class Validate {
    companion object {
        fun username(username: String): ValidateResponse {
            val isUsernameShortEnough = username.length <= 20
            val isUsernameLongEnough = username.length >= 3
            if (isUsernameShortEnough && isUsernameLongEnough)
                return ValidateResponse(true)

            return ValidateResponse(false, errorMessage = "Username has to be at least 3 characters and at most 20 characters")
        }
    }
}

class ValidateResponse(
    private val isValidated: Boolean,
    private val errorMessage: String = ""
){
    fun getIsValidated(): Boolean {
        return isValidated
    }

    fun getErrorMessage(): String {
        return errorMessage
    }
}