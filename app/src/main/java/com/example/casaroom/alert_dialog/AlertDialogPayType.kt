package com.example.casaroom.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.adapter.PayTypeButtonAdapter
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB


class AlertDialogPayType(private val context: Context): AlertDialog(context){
    private lateinit var payButton: PayTypeButtonAdapter


    val dialigViewPay = LayoutInflater.from(context).inflate(R.layout.payment_pay, null)
    val sum: TextView = dialigViewPay.findViewById(R.id.tvSum)
    val sumRest: TextView = dialigViewPay.findViewById(R.id.tvRest)
    val inputSum: EditText = dialigViewPay.findViewById(R.id.edInployted)
    val recyclerPay = dialigViewPay.findViewById<RecyclerView>(R.id.rcPayTupe)

    fun paiment(paymentTypes: List<PaymentTypeDB>, totalPayment: Double){
        payButton = PayTypeButtonAdapter()
        setContentView(dialigViewPay)
        sum.text = totalPayment.toString()
        val countInput = inputSum.text.toString()
        val numberInput = countInput.toDoubleOrNull()
        if (numberInput == 0.0 || countInput.isNotEmpty()){
            inputSum.hint = sum.toString()
        }else{
            recyclerPay.layoutManager = GridLayoutManager(context, 3)
            recyclerPay.adapter = payButton
            payButton.submitList(paymentTypes)
            Toast.makeText(context, "Sum is null or 0", Toast.LENGTH_LONG).show()
        }

    }
}