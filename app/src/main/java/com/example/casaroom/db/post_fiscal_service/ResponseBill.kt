package com.example.casaroom.db.post_fiscal_service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBill(
    @SerialName("ErrorCode")
    val ErrorCode: Int,
    @SerialName("ErrorMessage")
    val ErrorMessage: String
)