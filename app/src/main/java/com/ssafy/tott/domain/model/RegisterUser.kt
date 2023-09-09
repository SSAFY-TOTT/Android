package com.ssafy.tott.domain.model

data class RegisterUser(
    val id: String,
    val password: String,
    val password2: String,
    val phone: String,
    val accountNum: String
) {
    fun isValid() = blankCheck(id, password, password2, phone, accountNum).not()

    private fun blankCheck(vararg str: String): Boolean {
        return str.all { it.isNotBlank() }
    }
}
