package com.example.casaroom.modelsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casaroom.roomDB.assortiment.IsFolderDB
import com.example.casaroom.roomDB.dao_setting.DaoFolder

class ParentView(private  val dao: DaoFolder): ViewModel() {
    fun folder(): LiveData<List<IsFolderDB>>{
        return dao.getFolder()
    }
}