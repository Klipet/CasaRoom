package com.example.casaroom.modelsView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.casaroom.roomDB.dao_setting.DaoAssortiment

class ViewModelFactory(private val yourDao: DaoAssortiment, private val parentID: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AslModelList::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AslModelList(yourDao, parentID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}