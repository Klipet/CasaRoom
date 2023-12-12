package com.example.casaroom.roomDB.assortiment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assortiment")
data class AsortimentDB(
    @ColumnInfo(name = "AllowDiscounts")
    var AllowDiscounts: Boolean?,
    @ColumnInfo(name = "AllowNonInteger")
    var AllowNonInteger: Boolean?,
    @ColumnInfo(name = "Code")
    var Code: String?,
    @ColumnInfo(name = "EnableSaleTimeRange")
    var EnableSaleTimeRange: Boolean?,
    @PrimaryKey(autoGenerate = false)
    var ID: String,
    @ColumnInfo(name = "IsFolder")
    var IsFolder: Boolean?,
    @ColumnInfo(name = "parentID")
    var parentID: String?,
    @ColumnInfo(name = "Marking")
    var Marking: String?,
    @ColumnInfo(name = "Name")
    var Name: String?,
    @ColumnInfo(name = "Price")
    var Price: Double?,
    @ColumnInfo(name = "PriceLineEndDate")
    var PriceLineEndDate: String?,
    @ColumnInfo(name = "PriceLineId")
    var PriceLineId: String?,
    @ColumnInfo(name = "PriceLineStartDate")
    var PriceLineStartDate: String?,
    @ColumnInfo(name = "Promo")
    var Promo: List<PromoDB>?,
    @ColumnInfo(name = "ShortName")
    var ShortName: String?,
    @ColumnInfo(name = "StockBalance")
    var StockBalance: Int?,
    @ColumnInfo(name = "StockBalanceDate")
    var StockBalanceDate: Int?,
    @ColumnInfo(name = "Unit")
    var Unit: String?,
    @ColumnInfo(name = "VAT")
    var VAT: Double?,
    @ColumnInfo(name = "VATQuote")
    var VATQuote: String?,
    @ColumnInfo(name = "WeightSale")
    var WeightSale: Boolean?
)
