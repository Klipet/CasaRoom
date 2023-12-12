package com.example.casaroom.db.assortiment

data class Assortment(
    val AllowDiscounts: Boolean,
    val AllowNonInteger: Boolean,
    val Barcodes: List<String>,
    val Code: String,
    val EnableSaleTimeRange: Boolean,
    val ID: String,
    val IsFolder: Boolean,
    val Marking: String,
    val Name: String,
    val ParentID: String,
    val Price: Double,
    val PriceLineEndDate: Any,
    val PriceLineId: String,
    val PriceLineStartDate: String,
    val Promotions: List<Promotion>,
    val SaleEndTime: Any,
    val SaleStartTime: Any,
    val ShortName: String,
    val StockBalance: Int,
    val StockBalanceDate: Int,
    val Unit: String,
    val VAT: Double,
    val VATQuote: String,
    val WeightSale: Boolean
)
