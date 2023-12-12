package com.example.casaroom.roomDB.converters

import androidx.room.TypeConverter
import com.example.casaroom.roomDB.assortiment.BarcodesDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConvertBarcodes {
    @TypeConverter
    fun fromStringBarcode(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val stringArray = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val stringList: MutableList<String> = ArrayList()
        for (s in stringArray) {
            stringList.add(s)
        }
        return stringList
    }

    @TypeConverter
    fun toStringBarcode(stringList: List<String?>?): String? {
        if (stringList == null) {
            return null
        }
        val stringBuilder = StringBuilder()
        for (s in stringList) {
            stringBuilder.append(s).append(",")
        }
        return stringBuilder.toString()
    }
}