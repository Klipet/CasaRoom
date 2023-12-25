package com.example.casaroom.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.adapter.PayTypeButtonAdapter
import com.example.casaroom.clases.RegisterBill
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import kotlin.math.abs
import kotlin.math.absoluteValue


class AlertDialogPayType(private val context: Context){
    private lateinit var payButton: PayTypeButtonAdapter


    val alertDialogPay = AlertDialog.Builder(context)
    val dialigViewPay = LayoutInflater.from(context).inflate(R.layout.payment_pay, null)
    val sum: TextView = dialigViewPay.findViewById(R.id.tvSum)
    val sumRest: TextView = dialigViewPay.findViewById(R.id.tvRest)
    val ostSdac: TextView = dialigViewPay.findViewById(R.id.tvSdacea)
    val inputSum: EditText = dialigViewPay.findViewById(R.id.edInployted)
    val recyclerPay = dialigViewPay.findViewById<RecyclerView>(R.id.rcPayTupe)

    fun paiment(paymentTypes: List<PaymentTypeDB>, totalPayment: Double, asl: List<BillListDB>){
        try {
            var restDefault = "0"
            payButton = PayTypeButtonAdapter()
            alertDialogPay.setView(dialigViewPay)
            sum.text = totalPayment.toString()
            sumRest.text = restDefault
            inputSum.hint = totalPayment.toString()
            inputSum.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val decimalDigitsInputFilter = InputFilter { source, _, _, dest, dstart, dend ->
                        val regex = Regex("^\\d{0,8}([.]\\d{0,2})?\$")
                        val newText = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend)
                        if (newText == "." || newText == "0.") return@InputFilter null
                        if (newText == "0" && dest.toString() == "0") return@InputFilter null
                        return@InputFilter if (newText.matches(regex)) null else ""
                    }
                    inputSum.filters = arrayOf(decimalDigitsInputFilter)
                }
                override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                    val inputLength = p0?.toString()
                    if(inputLength.isNullOrEmpty()){
                        inputSum.setSelection(restDefault.toInt())
                        sumRest.text = totalPayment.toString()
                    }else{
                        val rest = totalPayment - inputLength!!.toDouble()
                        sumRest.text = rest.toString()
                        if (rest < 0){
                            ostSdac.text = "Сдачя"
                            val sdacea = abs(rest)
                            sumRest.text = sdacea.toString()
                            return
                        }else{
                            ostSdac.text = "Остаток"
                            return
                        }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {


                }
            })
            recyclerPay.layoutManager = GridLayoutManager(context, 4)
            recyclerPay.adapter = payButton
            payButton.submitList(paymentTypes)
            alertDialogPay.show()
        }catch (e: Exception){
            Log.d("Error add to pay", e.message.toString())
        }
    }
}