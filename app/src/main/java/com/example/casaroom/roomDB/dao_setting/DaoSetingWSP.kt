package com.example.casaroom.roomDB.dao_setting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.casaroom.db.seting_workspace.FiscalDevice
import com.example.casaroom.roomDB.assortiment.PromoDB
import com.example.casaroom.roomDB.work_seting.FiscalDeviceDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB

@Dao
interface DaoSetingWSP {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFiscalDevice(fiscalDevice: FiscalDeviceDB)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(paymentTypeDB: List<PaymentTypeDB>)

    @Query("select * from payment_type")
    fun selectPayment(): List<PaymentTypeDB>
    @Query("select * from fiscal_device")
    fun selectFiscalDevice(): FiscalDeviceDB
}