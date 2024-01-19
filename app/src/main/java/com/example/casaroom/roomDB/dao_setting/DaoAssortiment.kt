package com.example.casaroom.roomDB.dao_setting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.casaroom.roomDB.assortiment.AsortimentDB

@Dao
interface DaoAssortiment {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsl(asl: List<AsortimentDB>)

    @Query("Select * from assortiment where IsFolder = 0")
    fun getAsl(): LiveData<List<AsortimentDB>>

    @Query("Select * from assortiment where parentID =:parentID")
    fun getAslParent(parentID: String): LiveData<List<AsortimentDB>>

    @Query("Select * from assortiment where ID=:id")
    fun getAslID(id: String): List<AsortimentDB>


    @Query("select * from assortiment where Name like :query or Code like :query or barcode like :query ")
    fun searchAsl(query: String): List<AsortimentDB>
}