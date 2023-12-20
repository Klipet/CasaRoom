package com.example.casaroom.roomDB.work_seting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fiscal_device")
data class FiscalDeviceDB(
    @PrimaryKey(autoGenerate = false)
    var model: Int,
    @ColumnInfo("name")
    var Name: String?,
    @ColumnInfo("password")
    var password: String?,
    @ColumnInfo("port")
    var port: Int?,
    @ColumnInfo("port_speed")
    var portSpeed: Int?,
    @ColumnInfo("serviceUrl")
    var serviceUrl: String?
)
