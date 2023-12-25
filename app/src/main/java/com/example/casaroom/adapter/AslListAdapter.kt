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
import com.example.casaroom.databinding.ItemAslCardBinding
import com.example.casaroom.roomDB.assortiment.AsortimentDB

class AslListAdapter: ListAdapter<AsortimentDB, AslListAdapter.Holder>(Compact()) {

    private var alertDialogListner: AlertDialogListner? = null

    fun setAlertDialogListener(listener: AlertDialogListner) {
        alertDialogListner = listener
    }
    class Holder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemAslCardBinding.bind(view)
        fun bind(item: AsortimentDB) = with(binding) {
            tvNameAsl.text = item.Name
            tvPrice.text = String.format("%.2f", item.Price)
            itemView.setOnClickListener {
                showAlertDialog(item.Name.toString(), item.Price!!.toDouble(),
                    item.ID, item.VATQuote.toString(), item.Unit.toString(), itemView.context)

            }
        }
        private fun showAlertDialog(item: String, priceItem: Double, idAsl: String, tva: String, unit: String, context: Context) {
            AlertDialogAslToBill(context).onCloc(item, priceItem, idAsl,tva, unit, context)
        }
    }



    class Compact: DiffUtil.ItemCallback<AsortimentDB>() {
        override fun areItemsTheSame(oldItem: AsortimentDB, newItem: AsortimentDB): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(oldItem: AsortimentDB, newItem: AsortimentDB): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_asl_card, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       val item = getItem(position)
       holder.bind(item)
    }
}