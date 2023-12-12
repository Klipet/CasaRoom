package com.example.casaroom.roomDB.assortiment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder")
data class IsFolderDB(
    @PrimaryKey(autoGenerate = false)
    var IDAsl: String,
    @ColumnInfo("folder")
    var isFolder: Boolean?,
    @ColumnInfo("folderID")
    var folderID: String?,
    @ColumnInfo("Name")
    var name: String
)
