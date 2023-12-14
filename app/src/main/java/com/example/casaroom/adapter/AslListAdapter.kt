package com.example.casaroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.databinding.ItemAslCardBinding
import com.example.casaroom.roomDB.assortiment.AsortimentDB

class AslListAdapter: ListAdapter<AsortimentDB, AslListAdapter.Holder>(Compact()) {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemAslCardBinding.bind(view)
        fun bind(item: AsortimentDB) = with(binding) {
            tvNameAsl.text = item.Name
            tvPrice.text = item.Price.toString().format("%.2f")
            itemView.setOnClickListener {

            }
        }

    }

    class Compact: DiffUtil.ItemCallback<AsortimentDB>() {
        override fun areItemsTheSame(oldItem: AsortimentDB, newItem: AsortimentDB): Boolean {
            return oldItem == newItem
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