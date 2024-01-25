package com.example.casaroom.db.postBillSales

data class LineSales(
    val Count: Double,
    val CreatedByID: String,
    val CreationDate: String,
    val DeletedByID: String,
    val DeletionDate: String,
    val IsDeleted: Boolean,
    val Price: Double,
    val PriceLineID: String,
    val PromoPrice: Double,
    val Sum: Double,
    val SumWithDiscount: Double,
    val VATQuote: Double
)