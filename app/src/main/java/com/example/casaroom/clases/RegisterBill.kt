package com.example.casaroom.clases

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.db.post_fiscal_service.Line
import com.example.casaroom.db.post_fiscal_service.Payment
import com.example.casaroom.db.post_fiscal_service.RegisterFiscalReceipt
import com.example.casaroom.modelsView.BillModel
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterBill(private val paymentID: PaymentTypeDB) {
    private lateinit var db: DataBaseRoom
    private lateinit var bill: BillModel
    private lateinit var context: Context


    fun insertBill(aslID: List<BillListDB>) {
        db = DataBaseRoom.getDB(context)
        bill = BillModel(db)
//        val lines = aslID.map {
//            Line(Discount = 0.0,
//                Margin = 0.0,
//                Name = it.aslName,
//                Price = it.aslPrice,
//                Quantity = it.aslCouner,
//                UnitCode = 1,
//                UnitName = it.aslunitName,
//                VAT = it.aslVAT)
//
//        }
//        val linePayment = mutableListOf<Payment>()
//        val payment = paymentID.Code
//        getBilssSum {
//                val payment = Payment(
//                    it!!.toDouble(),
//                    payment.toString()
//                )
//            linePayment.add(payment)
//            }
//        val reqestBodyBill = RegisterFiscalReceipt(
//            ClientEmail = "",
//            ClientPhone = "",
//            FooterText = "Thank you!",
//            HeaderText = "",
//            Lines = lines,
//            Number = "2",
//            Payments = linePayment
//        )
//
//        val call = ApiFiscal.api.registerFiscalRecept(reqestBodyBill)
//        call.enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful){
//
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
//    private fun getBilssSum(callback: (Double?) -> Unit){
//        val billSum = BillModel(db)
//        billSum.getSumBill().observe(, Observer { sum ->
//            callback(sum)
//        })
//    }


    }
}

