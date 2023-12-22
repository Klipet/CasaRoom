package com.example.casaroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.alert_dialog.AlertDialogAslToBill
import com.example.casaroom.alert_dialog.AlertDialogListner
import com.example.casaroom.databinding.BillItemBinding
import com.example.casaroom.databinding.ItemAslCardBinding
import com.example.casaroom.roomDB.assortiment.AsortimentDB

import com.example.casaroom.roomDB.bill.BillListDB

class BillListAdapter(): ListAdapter<BillListDB, BillListAdapter.Holder>(Compact()), AlertDialogListner {

    private var alertDialogListner: AlertDialogListner? = null

    fun setAlertDialogListener(listener: AlertDialogListner) {
        alertDialogListner = listener
    }
    class Holder(view: View):RecyclerView.ViewHolder(view){
        private val binding = BillItemBinding.bind(view)
        fun bind(item: BillListDB) = with(binding) {
            tvItemName.text = item.aslName
            tvItemQuan.text = item.aslCouner.toString()
            tvItemSum.text = item.aslSum.toString()
        }

    }



    class Compact: DiffUtil.ItemCallback<BillListDB>() {
        override fun areItemsTheSame(oldItem: BillListDB, newItem: BillListDB): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BillListDB, newItem: BillListDB): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       val item = getItem(position)
       holder.bind(item)
    }

    override fun onItemAddedBill(item: String, counter: Int, sum: Double) {

    }
}