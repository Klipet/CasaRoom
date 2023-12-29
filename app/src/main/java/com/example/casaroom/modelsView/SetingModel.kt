package com.example.casaroom.modelsView

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casaroom.db.seting_workspace.FiscalDevice
import com.example.casaroom.db.seting_workspace.PaymentType
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.work_seting.FiscalDeviceDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetingModel(private val db: DataBaseRoom): ViewModel() {
    fun insertseting(fiscalDevice: FiscalDevice, paymentType: List<PaymentType>){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fiscalDeviceInsert = FiscalDeviceDB(
                    fiscalDevice.Model,
                    fiscalDevice.Name,
                    fiscalDevice.Password,
                    fiscalDevice.PortNumber,
                    fiscalDevice.PortSpeed,
                    fiscalDevice.ServiceUri

                )
                db.DaoSetingWSP().insertFiscalDevice(fiscalDeviceInsert)
            }catch (e: Exception){
                Log.d("Error FiscalDevice", e.message.toString())
            }
            try {
                val paimentInsert = paymentType.map {
                    PaymentTypeDB(
                        it.Code,
                        it.ExternalId,
                        it.Name,
                        it.PredefinedIndex,
                        it.PrintFiscalCheck
                    )

                }
                db.DaoSetingWSP().insertPayment(paimentInsert)
            }catch (e: Exception){
                Log.d("Error PaimentType", e.message.toString())
            }
        }
    }
    fun payType(): LiveData<List<PaymentTypeDB>>{
        return db.DaoSetingWSP().selectPayment()
    }
    fun payTypeToAlertDialog(): List<PaymentTypeDB>{
        return db.DaoSetingWSP().selectPaymentToDialog()
    }
}