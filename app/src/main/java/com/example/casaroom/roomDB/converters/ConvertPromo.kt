package com.example.casaroom.roomDB.converters

import androidx.room.TypeConverter
import com.example.casaroom.roomDB.assortiment.PromoDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConvertPromo {
    @TypeConverter
    fun fromJsonString(value: String?): List<PromoDB?>? {
        val listType: Type = object : TypeToken<List<PromoDB?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toJsonString(list: List<PromoDB?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}