package com.example.casaroom.roomDB.dao_setting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.casaroom.roomDB.bill.BillListDB


@Dao
interface DaoBillList {
    @Insert
    fun insertBillList(billList: BillListDB)
    @Query("select * from bill_List")
    fun selectBillList(): List<BillListDB>
    @Query("delete from bill_List")
    fun deleteBillList()

    @Query("delete from bill_List where `key` = :id" )
    fun deleteBillItem(id: Int)
    @Query("select sum(aslSum) from bill_List")
    fun getSumBill(): LiveData<Double>
    @Query("select sum(aslSum) from bill_List")
    fun getBillSum(): Double
    @Query("select * from bill_List")
    fun aslBillList(): LiveData<List<BillListDB>>

}