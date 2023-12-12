package com.example.casaroom.db.workspace

data class GetWorkplacesResult(
    val ErrorCode: Int,
    val ErrorText: String,
    val Workplaces: List<Workplace>
)
