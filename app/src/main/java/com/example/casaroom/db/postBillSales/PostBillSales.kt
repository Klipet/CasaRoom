package com.example.casaroom.db.postBillSales

data class PostBillSales(
    val Bills: List<BillSales>,
    val ShiftID: String,
    val Token: String
)