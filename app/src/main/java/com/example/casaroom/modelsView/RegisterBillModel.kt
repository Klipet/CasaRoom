package com.example.casaroom.modelsView

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.casaroom.alert_dialog.PaymentListener
import com.example.casaroom.api.API
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.api.RetrofitApi
import com.example.casaroom.db.post_fiscal_service.Line
import com.example.casaroom.db.post_fiscal_service.Payment
import com.example.casaroom.db.post_fiscal_service.RegisterFiscalReceipt
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterBillModel(): ViewModel(), PaymentListener{
    private lateinit var db: DataBaseRoom
    private lateinit var bill: BillModel

    fun getBillList(): List<BillListDB>{
        return db.DaoBillList().aslBillList()
    }


    override fun onPaymentDetailsEntered(amount: Double) {

    }
    fun createRegisterFiscal(paymentID: Int){
        val lines = getBillList()
        val payment = listOf(
            Payment(
                lines.firstOrNull()!!.aslSum,
                paymentID.toString()
            )
        )
        val registerBill = RegisterFiscalReceipt(
                  ClientPhone = "",
                  ClientEmail = "",
                  FooterText = "",
                  HeaderText = "",
                  Lines = lines.map{ lineses->
                      Line(
                          Discount = 0.0,
                          Margin = 0.0,
                          Name = lineses.aslName,
                          Price = lineses.aslPrice,
                          Quantity = lineses.aslCouner,
                          UnitCode = 1,
                          UnitName = lineses.aslunitName,
                          VAT = lineses.aslVAT
                      )
                  },
                  Number = "",
                  Payments = payment
              )
        postRegisterBill(registerBill)

    }
    private fun postRegisterBill(receipt: RegisterFiscalReceipt){
        val postReatrofit = API.api.registerFiscalRecept(receipt)
        postReatrofit.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }







}


