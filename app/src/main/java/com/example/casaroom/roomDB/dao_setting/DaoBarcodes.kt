package com.example.casaroom.roomDB.dao_setting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.casaroom.roomDB.assortiment.BarcodesDB

@Dao
interface DaoBarcodes {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBarcodes(barcodes: List<BarcodesDB>)


}