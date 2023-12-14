package com.example.casaroom.modelsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casaroom.roomDB.assortiment.AsortimentDB
import com.example.casaroom.roomDB.dao_setting.DaoAssortiment

class AslModelList (private val dao: DaoAssortiment, private val parentID: String): ViewModel() {
    fun aslData(): LiveData<List<AsortimentDB>> {

        return dao.getAslParent(parentID)
    }
}
