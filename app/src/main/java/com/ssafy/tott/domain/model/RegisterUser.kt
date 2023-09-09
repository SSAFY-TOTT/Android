package com.ssafy.tott.domain.model

data class RegisterUser(
    val id: String,
    val password: String,
    val validPassword: String,
    val phone: String,
    val accountNum: String
) {
    fun isValid() = blankCheck(id, password, validPassword, phone, accountNum).not() &&
            password == validPassword

    private fun blankCheck(vararg str: String): Boolean {
        return str.all { it.isNotBlank() }
    }
}
