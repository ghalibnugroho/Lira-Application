package com.wantobeme.lira.model

// response API after Login
data class GuestToken(
    val status: Int,
    val message: String,
    val token: String
)

data class Guest(
    var identitas: String = "",
    var email: String = "",
    var role: Int = 0,
)

