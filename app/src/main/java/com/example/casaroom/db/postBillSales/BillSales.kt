package com.example.casaroom.db.postBillSales

data class BillSales(
    var CardID: String,
    var ClientID: String,
    var ClosedByID: String,
    var ClosingDate: String,
    var CreationDate: String,
    var ID: String,
    var Lines: List<LineSales>,
    var Number: Long,
    var OpenedByID: String,
    var Payments: List<PaymentSales>
)