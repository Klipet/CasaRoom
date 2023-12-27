package com.example.casaroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB

class PayAdapter( private val payment: List<PaymentTypeDB>): RecyclerView.Adapter<PayAdapter.Holder>() {
    private var paymentAmount: Double = 0.0
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
        holder.button.text = payment[position].Name
    }
    fun updatePaymentAmount(newAmount: Double){
        paymentAmount = newAmount
        notifyDataSetChanged()
    }


}