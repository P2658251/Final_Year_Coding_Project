package com.example.final_year_coding_project


class Validate {
    companion object {
        fun username(username: String): ValidateResponse {
            val isUsernameShortEnough = username.length <= 20
            val isUsernameLongEnough = username.length >= 3
            if (!isUsernameShortEnough || !isUsernameLongEnough)
                return ValidateResponse(false, errorMessage = "Username has to be at least 3 characters and at most 20 characters")

            val acceptedCharactersRegex = Regex("^[a-zA-Z0-9._-]*$")
            val isUsernameOnlyUsingAcceptedCharacters = acceptedCharactersRegex.matches(username)
            if (!isUsernameOnlyUsingAcceptedCharacters)
                return ValidateResponse(false, errorMessage = "Username should only contain letters, numbers, fullstops, hyphens, and dashes")

            return ValidateResponse(true)
        }

        fun password(password: String): ValidateResponse{
            val isPasswordShortEnough = password.length <= 20
            val isPasswordLongEnough = password.length >= 8
            if (!isPasswordShortEnough || !isPasswordLongEnough)
                return ValidateResponse(false, errorMessage = "Password has to be at least 8 characters and at most 20 characters")

            val doesPasswordHaveAnUppercaseLetter = password.any{it.isUpperCase()}
            val doesPasswordHaveALowercaseLetter = password.any{it.isLowerCase()}
            val doesPasswordHaveADigit = password.any{it.isDigit()}
            val doesPasswordHaveASpecialCharacter = password.any { "!@#$%^&*()-_=+[]{};:'\",.<>?/\\|`~".contains(it) }
            if (!doesPasswordHaveAnUppercaseLetter || !doesPasswordHaveALowercaseLetter || !doesPasswordHaveADigit || !doesPasswordHaveASpecialCharacter)
                return ValidateResponse(false, errorMessage = "Password has have at least one uppercase letter, at least lowercase letter, at least one digit and at least one special character")

            val doesPasswordContainAnySpaces = password.contains(" ")
            if (doesPasswordContainAnySpaces)
                return ValidateResponse(false, errorMessage = "Password should not have any spaces in it")


            return ValidateResponse(true)
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