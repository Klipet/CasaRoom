package com.example.casaroom.db.save_bill_sales

data class SaveBillSales(
    val Bills: List<BillSales>,
    val ShiftID: String,
    val Token: String
)