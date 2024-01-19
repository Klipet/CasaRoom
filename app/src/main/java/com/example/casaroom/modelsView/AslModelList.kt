package com.example.casaroom.modelsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaroom.roomDB.assortiment.AsortimentDB
import com.example.casaroom.roomDB.dao_setting.DaoAssortiment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AslModelList (private val dao: DaoAssortiment, private val parentID: String): ViewModel() {
    private val _aslData = MutableLiveData<List<AsortimentDB>>()
    val aslDataList: LiveData<List<AsortimentDB>> get() = _aslData

    fun loadAslData() {
        dao.getAslParent(parentID).observeForever {
            _aslData.value = it
        }
    }

    fun searchAsl(query: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO){
                dao.searchAsl("%$query%")
            }
            _aslData.postValue(results)
        }

    }

}
