package com.example.casaroom.db.save_bill_sales

data class BillSales(
    val ClosedByID: String,
    val ClosingDate: String,
    val CreationDate: String,
    val ID: String,
    val Lines: List<LineSales>,
    val Number: Int,
    val OpenedByID: String,
    val Payments: List<PaymentSales>,
    val CardID: String,
    val ClientID: String,
)