package com.example.casaroom.roomDB.dao_setting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import com.example.casaroom.roomDB.assortiment.PromoDB

@Dao
interface DaoPromo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPromo(promo: List<PromoDB>)
}