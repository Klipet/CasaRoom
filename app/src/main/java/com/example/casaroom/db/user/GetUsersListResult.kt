package com.example.casaroom.db.user

data class GetUsersListResult(
    val ErrorCode: Int,
    val ErrorText: Any,
    val Users: List<UserX>
)
