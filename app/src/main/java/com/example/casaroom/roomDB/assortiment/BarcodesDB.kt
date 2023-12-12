package com.example.casaroom.roomDB.assortiment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcodes")
data class BarcodesDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo("aslID")
    var AslID: String?,
    @ColumnInfo("barcode")
    var barcode: List<String>?
)
