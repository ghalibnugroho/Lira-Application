package com.wantobeme.lira.model

// response API registrasi
data class Guest(
    val status: Int,
    val message: String
)

// response API after Login
data class UserToken(
    val status: Int,
    val message: String,
    val token: String
)

data class User(
    val identitas: String = "",
    val email: String = "",
    val role: Int = 0,
)

