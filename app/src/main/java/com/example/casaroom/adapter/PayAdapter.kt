package com.example.casaroom.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.casaroom.R
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.db.post_fiscal_service.Line
import com.example.casaroom.db.post_fiscal_service.Payment
import com.example.casaroom.db.post_fiscal_service.RegisterFiscalReceipt
import com.example.casaroom.db.save_bill_sales.SaveBillSales
import com.example.casaroom.modelsView.BillModel
import com.example.casaroom.modelsView.PostSalesBill
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import retrofit2.Call
import retrofit2.Callback
import kotlinx.serialization.json.Json
import okhttp3.OkHttp
import retrofit2.Response

class PayAdapter(private val payment: List<PaymentTypeDB>, private val amount: Double,
                 private val bill: List<BillListDB>, private val context: Context, private  val dialog: AlertDialog, private val progressBar: ProgressBar,
     private val sharedPreferences: SharedPreferences)
    : RecyclerView.Adapter<PayAdapter.Holder>() {
    private lateinit var db: DataBaseRoom
    private lateinit var billModel: BillModel
    private lateinit var billSave: PostSalesBill

    class Holder(item: View): RecyclerView.ViewHolder(item) {
        val button: Button = item.findViewById(R.id.btPaymentName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pay_button, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return payment.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val btpayment = payment[position]
        holder.button.text = btpayment.Name
        db = DataBaseRoom.getDB(context)

        holder.button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val lines = bill.map {
                Line(Discount = 0.0,
                    Margin = 0.0,
                    Name = it.aslName,
                    Price = it.aslPrice,
                    Quantity = it.aslCouner,
                    UnitCode = 1,
                    UnitName = it.aslunitName,
                    VAT = it.aslVAT)

            }
            val linePayment = listOf<Payment>(
                Payment(
                    Amount = amount,
                    Code = btpayment.Code.toString()
                )
            )
            val reqestBodyBill = RegisterFiscalReceipt(
                ErrorCode = "",
                ErrorMessage = "",
                ClientEmail = "",
                ClientPhone = "",
                FooterText = "Thank you!",
                HeaderText = "",
                Lines = lines,
                Number = "2",
                Payments = linePayment,
                ErrorCode = "",
                ErrorMessage = ""
            )
            val json = Gson()
            val call = ApiFiscal.api.registerFiscalRecept(reqestBodyBill)
           call.enqueue(object : Callback<RegisterFiscalReceipt> {
               override fun onResponse(
                   call: Call<RegisterFiscalReceipt>, response: Response<RegisterFiscalReceipt>) {
               //    lottiImage.visibility = View.VISIBLE

                   if (response.body()?.ErrorMessage.isNullOrEmpty()){
                       Toast.makeText(context," IS succesifull", Toast.LENGTH_LONG ).show()
                       controlSaveBill(btpayment.ExternalId)
                   }
                   else{
                   val errorBody = response.errorBody()?.string()
                   //val mesageError = Json.decodeFromString<ResponseBill>(errorBody?:  "")
                   //val errorCode = mesageError.ErrorCode
                   if (response.body()?.ErrorMessage.isNullOrEmpty()){
                       Toast.makeText(context," IS succesifull", Toast.LENGTH_LONG ).show()
                       Log.d("Error body", response.body().toString())
                   } else{
                       Toast.makeText(context, "Empty response body", Toast.LENGTH_LONG).show()

                      // handleErrorResponse(errorCode, errorBody)
                   }
               }
               override fun onFailure(call: Call<RegisterFiscalReceipt>, t: Throwable) {
                   Toast.makeText(context," Error", Toast.LENGTH_LONG ).show()
               }
               private fun handleErrorResponse(errorCode: Int, errorBody: String?) {
                   // Обработка ошибки с использованием тела ответа (если оно присутствует)
                   if (!errorBody.isNullOrBlank()) {
                       // Здесь вы можете выполнить обработку тела ответа с ошибкой
                       Toast.makeText(context, "Error response body: $errorBody", Toast.LENGTH_LONG).show()
                   } else {
                       // Обработка, если тело ответа отсутствует
                       Toast.makeText(context, "Unsuccessful response: $errorCode", Toast.LENGTH_LONG).show()
                   }
               }
           })
        }
    }
    fun controlSaveBill(paymentName: String){
        billSave = PostSalesBill(payment, amount, bill, sharedPreferences, paymentName)
        val saveBillSales = billSave.postBillModel()
        saveBillSales.enqueue(object : Callback<SaveBillSales>{
            override fun onResponse(call: Call<SaveBillSales>, response: Response<SaveBillSales>) {
                val responses = response.body()
                if (response.isSuccessful){
                    progressBar.visibility = View.GONE
                    billModel = BillModel(db)
                    billModel.deleteBill()
                    dialog.dismiss()
                }else{
                    Log.d("Error Response Save Bill", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<SaveBillSales>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}


