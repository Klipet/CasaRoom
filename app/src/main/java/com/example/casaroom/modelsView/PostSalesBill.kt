package com.example.casaroom.modelsView

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
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
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

class PostSalesBill(private val payment: List<PaymentTypeDB>, private val amount: Double,
                    private val bill: List<BillListDB>, private val sharedPreferences: SharedPreferences,
                    private val paymentName: String): ViewModel() {



    fun postBillModel(): Call<SaveBillSales> {
        val standardDate = Date()
       // Получаем миллисекунды
        val timestamp = standardDate.time
        // Получаем текущее смещение времени относительно UTC
        val timeZoneOffset = TimeZone.getDefault().rawOffset / (60 * 1000)
        // Складываем миллисекунды и смещение времени
        val combinedTime = timestamp + timeZoneOffset / 4
        // Форматируем в строку в формате \/Date(...)\/
        val formattedDate = "/Date($combinedTime+${String.format("%04d", timeZoneOffset)})/"
        Log.d("Data", formattedDate)
        var number = 1
        val token = sharedPreferences.getString(Constant.TOKEN, "Non").toString()
        val lines = bill.map {
            LineSales(
                Count = it.aslCouner,
                CreationDate = formattedDate,
                CreatedByID = "a5b29797-d56c-45df-8cc4-d33db475edfb",
                IsDeleted = true,
                Price = it.aslPrice,
                PriceLineID = it.aslPriceLine,
                PromoPrice = 0.0,
                Sum = it.aslSum,
                SumWithDiscount = it.aslSum,
                VATQuote = it.VATQuoti,
                DeletedByID = "00000000-0000-0000-0000-000000000000",
                DeletionDate = "/Date(000000000000+0000)/"
            )
        }
        val paymentSales = listOf<PaymentSales>(
            PaymentSales(
                CreationDate = formattedDate,
                CreatedByID = token,
                ID = "a5b29797-d56c-45df-8cc4-d33db475edfb",
                PaymentTypeID = paymentName,
                Sum = amount
            )
        )
        val bill = listOf<BillSales>(
            BillSales(
                ClosedByID = token,
                ClosingDate = formattedDate,
                CreationDate = formattedDate,
                ID = "00000000-0000-0000-0000-000000000000",
                Lines = lines,
                Number = number++,
                OpenedByID = "a5b29797-d56c-45df-8cc4-d33db475edfb",
                Payments = paymentSales,
                CardID = "00000000-0000-0000-0000-000000000000",
                ClientID = "00000000-0000-0000-0000-000000000000"
            )
        )
        val saveBillSales = SaveBillSales(
            Bills = bill,
            Token = token,
            ShiftID = "1627aea5-8e0a-4371-9022-9b504344e724",
            )
        return API.api.postBill(saveBillSales)
    }
}