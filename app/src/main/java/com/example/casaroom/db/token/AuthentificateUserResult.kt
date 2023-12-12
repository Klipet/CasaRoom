package com.example.casaroom.db.token

data class AuthentificateUserResult(
    val ErrorCode: Int,
    val ErrorText: String,
    val Token: String,
    val TokenValidTo: String
)
