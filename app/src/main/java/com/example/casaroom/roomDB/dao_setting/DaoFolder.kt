package com.example.casaroom.roomDB.dao_setting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.casaroom.roomDB.assortiment.IsFolderDB

@Dao
interface DaoFolder {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFolder(isFolder: List<IsFolderDB>)

    @Query("select * from folder")
    fun getFolder(): LiveData<List<IsFolderDB>>
}