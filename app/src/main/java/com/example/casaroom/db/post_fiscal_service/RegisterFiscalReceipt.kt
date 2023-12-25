package com.example.casaroom.db.post_fiscal_service

data class RegisterFiscalReceipt(
    val ClientEmail: String,
    val ClientPhone: String,
    val FooterText: String,
    val HeaderText: String,
    val Lines: List<Line>,
    val Number: String,
    val Payments: List<Payment>
)