package com.example.casaroom.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casaroom.R
import com.example.casaroom.adapter.BillListAdapter
import com.example.casaroom.databinding.FragmentBonListBinding
import com.example.casaroom.modelsView.BillModel
import com.example.casaroom.roomDB.DataBaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BonListFragment : Fragment() {
    private lateinit var binding: FragmentBonListBinding
    private lateinit var db: DataBaseRoom
    private lateinit var billAdapter: BillListAdapter
    private lateinit var  billModel: BillModel


    override fun onCreate(savedInstanceState: Bundle?) {
        db = context?.let { DataBaseRoom.getDB(it.applicationContext) }!!
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        binding = FragmentBonListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val view = binding.root
        initBil()
        return view
        //return inflater.inflate(R.layout.fragment_bon_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BonListFragment()
    }

    fun initBil(){
        billModel = BillModel(db)
        billAdapter = BillListAdapter()
        binding.rcBillList.layoutManager = LinearLayoutManager(context)
        binding.rcBillList.adapter = billAdapter
        val billList =  billModel.getBill()
        billList.observe(viewLifecycleOwner, Observer {
            Log.d("Count item BillList", it.size.toString())
        //    binding.rcBillList.scrollToPosition(it.size-2)
            binding.rcBillList.layoutManager?.scrollToPosition(it.size + 2)
        //    binding.rcBillList.offsetChildrenVertical(it.size)
            billAdapter.submitList(it)
        })
    }
}