package com.example.casaroom.db.post_fiscal_service

data class RegisterFiscalReceipt(
    val ErrorCode: String,
    val ErrorMessage: String,
    val ClientEmail: String,
    val ClientPhone: String,
    val FooterText: String,
    val HeaderText: String,
    val Lines: List<Line>,
    val Number: String,
    val Payments: List<Payment>,
    var ErrorCode: String,
    var ErrorMessage: String
)