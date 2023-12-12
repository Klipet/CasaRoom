package com.example.casaroom.roomDB.assortiment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "promo")
data class PromoDB (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo("aslID")
    var AslID: String?,
    @ColumnInfo("allowDiscount")
    var AllowDiscount: Boolean?,
    @ColumnInfo("endDate")
    var EndDate: String?,
    @ColumnInfo("idPromo")
    var IDPromo: String?,
    @ColumnInfo("promoPrice")
    var Price: Double?,
    @ColumnInfo("startDate")
    var StartDate: String?,
    @ColumnInfo("timeBegin")
    var TimeBegin: String?,
    @ColumnInfo("timeEnd")
    var TimeEnd: String?
)
