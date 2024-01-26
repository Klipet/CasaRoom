package com.example.casaroom.modelsView

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.casaroom.api.API
import com.example.casaroom.constant.Constant
import com.example.casaroom.db.save_bill_sales.BillSales
import com.example.casaroom.db.save_bill_sales.LineSales
import com.example.casaroom.db.save_bill_sales.PaymentSales
import com.example.casaroom.db.save_bill_sales.SaveBillSales
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.TimeZone

class PostSalesBill(private val payment: List<PaymentTypeDB>, private val amount: Double,
                    private val bill: List<BillListDB>, private val sharedPreferences: SharedPreferences): ViewModel() {


    fun postBillModel(): Call<SaveBillSales> {
        var number = 1
        val token = sharedPreferences.getString(Constant.TOKEN, "Non").toString()
        val lines = bill.map {
            LineSales(
                Count = it.aslCouner,
                CreationDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now(ZoneId.systemDefault()).toString()
                } else {
                }.toString(),
                CreatedByID = token,
                IsDeleted = false,
                Price = it.aslPrice,
                PriceLineID = it.aslPriceLine,
                PromoPrice = 0.0,
                Sum = it.aslSum,
                SumWithDiscount = it.aslSum,
                VATQuote = it.VATQuoti
            )
        }
        val paymentSales = listOf<PaymentSales>(
            PaymentSales(
                CreationDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now(ZoneId.systemDefault()).toString()
                } else {
                }.toString(),
                CreatedByID = token,
                ID = number++.toString(),
                PaymentTypeID = payment.toString(),
                Sum = amount
            )
        )
        val bill = listOf<BillSales>(
            BillSales(
                ClosedByID = token,
                ClosingDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now(ZoneId.systemDefault()).toString()
                } else {
                }.toString(),
                CreationDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now(ZoneId.systemDefault()).toString()
                } else {
                }.toString(),
                ID = "",
                Lines = lines,
                Number = number++,
                OpenedByID = token,
                Payments = paymentSales
            )
        )
        val saveBillSales = SaveBillSales(
            Bills = bill,
            Token = token,
            ShiftID = "1",
            )
        return API.api.postBill(saveBillSales)
    }
}