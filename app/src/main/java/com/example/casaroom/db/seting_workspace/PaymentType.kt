package com.example.casaroom.db.seting_workspace

data class PaymentType(
    val Code: String,
    val ExternalId: String,
    val Name: String,
    val PredefinedIndex: Int,
    val PrintFiscalCheck: Boolean
)
