package com.example.casaroom.roomDB.bill

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_List")
data class BillListDB(
    @PrimaryKey(autoGenerate = true)
    val key: Int,
    @ColumnInfo("aslUid")
    var aslUid: String,
    @ColumnInfo("aslName")
    var aslName: String,
    @ColumnInfo("aslPrice")
    var aslPrice: Double,
    @ColumnInfo("aslCounter")
    var aslCouner: Double,
    @ColumnInfo("aslSum")
    var aslSum: Double,
    @ColumnInfo("VAT")
    var aslVAT: String,
    @ColumnInfo("unit_code")
    var aslunitCode: String,
    @ColumnInfo("unit_name")
    var aslunitName: String

)