package com.example.casaroom.modelsView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BillModel(private val db: DataBaseRoom): ViewModel() {
    fun getBill(): LiveData<List<BillListDB>> {
        return db.DaoBillList().aslBillList()
    }
    fun getSumBill(): LiveData<Double>{
        return db.DaoBillList().getSumBill()
    }
    fun getBillSumToDialog(): Double{
        return db.DaoBillList().getBillSum()
    }
    fun getBillToDialog(): List<BillListDB> {
        return db.DaoBillList().selectBillList()
    }
    fun deleteBill(){
        CoroutineScope(Dispatchers.IO).launch {
            db.DaoBillList().deleteBillList()
        }
    }


}