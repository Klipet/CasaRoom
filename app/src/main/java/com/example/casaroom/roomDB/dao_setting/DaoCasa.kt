package com.example.casaroom.roomDB.dao_setting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.casaroom.roomDB.casa.CasaDB

@Dao
interface DaoCasa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCasa(casa: CasaDB)

    @Update
    fun updateCasa(casa: CasaDB)

    @Query("Delete from casa")
    fun deleteCasa()

    @Query("select * from casa")
    fun getAllCasa(): CasaDB

}