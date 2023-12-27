package com.example.casaroom.adapter

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.alert_dialog.PaymentListener
import com.example.casaroom.databinding.ItemPayButtonBinding
import com.example.casaroom.modelsView.RegisterBillModel
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB

class PayTypeButtonAdapter (): ListAdapter<PaymentTypeDB, PayTypeButtonAdapter.Holder>(CompactPay()) {



    class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemPayButtonBinding.bind(view)
        private var paymentEnteredListener: PaymentListener? = null
        fun bind(item: PaymentTypeDB?) {
            binding.btPaymentName.text = item?.Name
            itemView.setOnClickListener {
                RegisterBillModel().createRegisterFiscal(item!!.Code!!.toInt())
            }
        }

    }

    class CompactPay: DiffUtil.ItemCallback<PaymentTypeDB>() {
        override fun areItemsTheSame(oldItem: PaymentTypeDB, newItem: PaymentTypeDB): Boolean {
            return oldItem.ExternalId == newItem.ExternalId
        }

        override fun areContentsTheSame(oldItem: PaymentTypeDB, newItem: PaymentTypeDB): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pay_button, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}