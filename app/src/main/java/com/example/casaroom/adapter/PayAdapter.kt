package com.example.casaroom.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.alert_dialog.AlertDialogPayType
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.clases.RegisterBill
import com.example.casaroom.constant.Constant
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

class PayAdapter( private val payment: List<PaymentTypeDB>, private val amount: Double,
                  private val bill: List<BillListDB>, private val context: Context, private  val dialog: AlertDialog
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
                Payments = linePayment
            )

            val call = ApiFiscal.api.registerFiscalRecept(reqestBodyBill)
            call.enqueue(object : Callback<RegisterFiscalReceipt> {
                override fun onResponse(call: Call<RegisterFiscalReceipt>, response: Response<RegisterFiscalReceipt>) {
                    if (response.isSuccessful){
                        Toast.makeText(context," IS succesifull", Toast.LENGTH_LONG ).show()
                        billModel = BillModel(db)
                        billModel.deleteBill()
                        dialog.dismiss()
                    }
                }
                override fun onFailure(call: Call<RegisterFiscalReceipt>, t: Throwable) {
                    Toast.makeText(context," Error", Toast.LENGTH_LONG ).show()
                }

            })
        }
    }


}