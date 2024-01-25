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
import com.example.casaroom.R
import com.example.casaroom.api.API
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.constant.Constant
import com.example.casaroom.db.postBillSales.BillSales
import com.example.casaroom.db.postBillSales.LineSales
import com.example.casaroom.db.postBillSales.PaymentSales
import com.example.casaroom.db.postBillSales.PostBillSales
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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone
import java.util.UUID

class PayAdapter(private val payment: List<PaymentTypeDB>, private val amount: Double,
                 private val bill: List<BillListDB>, private val context: Context, private  val dialog: AlertDialog,
    private val progressBar: ProgressBar, private val sharedPreferences: SharedPreferences
): RecyclerView.Adapter<PayAdapter.Holder>() {
    private lateinit var db: DataBaseRoom
    private lateinit var billModel: BillModel

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
                ClientEmail = "",
                ClientPhone = "",
                FooterText = "Thank you!",
                HeaderText = "",
                Lines = lines,
                Number = "2",
                Payments = linePayment,
                ErrorCode = "",
                ErrorMesage = ""
            )

            val call = ApiFiscal.api.registerFiscalRecept(reqestBodyBill)
           call.enqueue(object : Callback<RegisterFiscalReceipt> {
               @RequiresApi(Build.VERSION_CODES.O)
               override fun onResponse(
                   call: Call<RegisterFiscalReceipt>, response: Response<RegisterFiscalReceipt>) {
                   progressBar.visibility = View.GONE
                   val response = response.body()
                   if (response?.ErrorMesage.isNullOrEmpty()){
                       Toast.makeText(context," ISFiscalService", Toast.LENGTH_LONG ).show()
                       postSales(btpayment)
                   }
                   else{
                       Toast.makeText(context, "Not Connect to FiscalService", Toast.LENGTH_LONG).show()

                   }
               }
               override fun onFailure(call: Call<RegisterFiscalReceipt>, t: Throwable) {
                   Toast.makeText(context," Error", Toast.LENGTH_LONG ).show()
               }
           })
        }



    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun postSales(paymentSelected: PaymentTypeDB){
        val token = sharedPreferences.getString(Constant.TOKEN, "Non")!!
        val standartDate = Date()
        val timespec = standartDate.time
        val timeZone = TimeZone.getDefault().rawOffset / (60*1000)
        val combiner = timespec + timeZone * 60 * 1000
        val formatStringData = "\\/Date($combiner+${String.format("%04d", timeZone)})\\/"
        val linesSales = bill.map {
            LineSales(
                Count = it.aslCouner,
                CreatedByID = token,
                CreationDate = formatStringData,
                DeletedByID = "",
                DeletionDate = "",
                IsDeleted = false,
                Price = it.aslPrice,
                PriceLineID = it.priceLine,
                PromoPrice = 0.0,
                Sum = it.aslSum,
                SumWithDiscount = it.aslSum,
                VATQuote = it.codeTVA
            )
        }
        val sum = bill[0].aslSum

        val paymentSales = listOf<PaymentSales>(
            PaymentSales(
                CreatedByID = token,
                CreationDate = formatStringData,
                ID = UUID.randomUUID().toString(),
                PaymentTypeID = paymentSelected.PredefinedIndex.toString(),
                Sum = sum
            )
        )
        val billList = mutableListOf<BillSales>()
        billList.addAll( linesSales.mapIndexed { index, line ->
            BillSales(
                CardID = "",
                ClientID = "",
                ID = UUID.randomUUID().toString(),
                ClosedByID = token,
                Number = (index + 1).toLong(),
                OpenedByID = token,
                CreationDate = formatStringData,
                ClosingDate = formatStringData,
                Lines = linesSales,
                Payments = paymentSales
            )
        }

        )
        val postBill = PostBillSales(
            Bills = billList,
            ShiftID = "2",
            Token = UUID.randomUUID().toString()
        )

        val apiPost = API.api.postBillSales(postBill)
        apiPost.enqueue(object : Callback<PostBillSales>{
            override fun onResponse(call: Call<PostBillSales>, response: Response<PostBillSales>) {
                try {
                    if (response.isSuccessful){
                        billModel = BillModel(db)
                        billModel.deleteBill()
                        dialog.dismiss()
                    }
                }catch (e: Exception){
                   Log.d("Post SaveBill", e.toString())
                }
            }

            override fun onFailure(call: Call<PostBillSales>, t: Throwable) {
                Log.d("reqest save Bill", t.message.toString())
            }

        })
    }



}


