package com.example.casaroom.roomDB.casa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "casa")
data class CasaDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "casaID")
    var casaID: String,
    @ColumnInfo(name = "casaName")
    var casaName: String

)
