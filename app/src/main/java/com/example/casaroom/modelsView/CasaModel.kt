package com.example.casaroom.modelsView

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaroom.clases.StartActivity
import com.example.casaroom.db.workspace.GetWorkplacesResult
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.casa.CasaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
class CasaModel(private  val db: DataBaseRoom): ViewModel() {
     fun casaToDB(casaID: String, casaName:String ){
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 if (db.DaoCasa().getAllCasa() != null){
                     db.DaoCasa().deleteCasa()
                     val insertCasa =
                         CasaDB(
                             casaID = casaID,
                             casaName = casaName
                         )
                     db.DaoCasa().insertCasa(insertCasa)
                 }else{
                     val insertCasa =
                         CasaDB(
                             casaID = casaID,
                             casaName = casaName
                         )
                     db.DaoCasa().insertCasa(insertCasa)

                 }
             } catch (e: Exception) {
                 Log.d("Error Casa", e.toString())
             }
         }
    }
    suspend fun getCasa(): CasaDB{
        val casaName = db.DaoCasa().getAllCasa()
        return casaName
    }
}