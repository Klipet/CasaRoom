package com.example.casaroom.db.post_fiscal_service

data class Line(
    val Discount: Double,
    val Margin: Double,
    val Name: String,
    val Price: Double,
    val Quantity: Double,
    val UnitCode: Int,
    val UnitName: String,
    val VAT: String
)