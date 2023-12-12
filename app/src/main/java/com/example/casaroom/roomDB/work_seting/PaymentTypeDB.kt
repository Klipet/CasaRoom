package com.example.casaroom.roomDB.work_seting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_type")
data class PaymentTypeDB(
    @ColumnInfo("code")
    var Code: String?,
    @PrimaryKey(autoGenerate = false)
    var ExternalId: String,
    @ColumnInfo("name")
    var Name: String?,
    @ColumnInfo("predefined_index")
    var PredefinedIndex: Int?,
    @ColumnInfo("print_fiscal_chek")
    var PrintFiscalCheck: Boolean?
)
