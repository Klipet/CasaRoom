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
    fun selectBillList(): LiveData<List<BillListDB>>
    @Query("delete from bill_List")
    fun deleteBillList()
    @Query("select sum(aslSum) from bill_List")
    fun getSumBill(): LiveData<Double>

}