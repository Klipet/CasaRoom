package com.example.casaroom.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.casaroom.R
import com.example.casaroom.adapter.AslListAdapter
import com.example.casaroom.alert_dialog.AlertDialogListner
import com.example.casaroom.databinding.FragmentAslListBlankBinding
import com.example.casaroom.modelsView.AslModel
import com.example.casaroom.modelsView.AslModelList
import com.example.casaroom.modelsView.ViewModelFactory
import com.example.casaroom.roomDB.DataBaseRoom

class AslListBlankFragment : Fragment(), AlertDialogListner {
    private lateinit var adapterAsl: AslListAdapter
    private lateinit var dataBaseRoom: DataBaseRoom
    private lateinit var bindingAsl: FragmentAslListBlankBinding
    private lateinit var group: String
    private lateinit var aslModel: AslModelList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingAsl = FragmentAslListBlankBinding.inflate(inflater, container, false)
        val view = bindingAsl.root
        dataBaseRoom = context?.let { DataBaseRoom.getDB(it.applicationContext) }!!
        group = requireArguments().getString(ARG_CATEGORY)!!
        init()
        return view
        // return inflater.inflate(R.layout.fragment_asl_list_blank, container, false)
    }

    companion object {

        private const val ARG_CATEGORY = "parentID"
        fun newInstance(group: String): AslListBlankFragment {
            val fragment = AslListBlankFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, group)
            fragment.arguments = args
            return fragment

        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun init(){
        try {
            val aslModelList = ViewModelProvider(this@AslListBlankFragment, ViewModelFactory(
                dataBaseRoom.DaoAssortiment(), requireArguments().getString(ARG_CATEGORY)!!
            )).get(AslModelList::class.java)

            adapterAsl = AslListAdapter()
            bindingAsl.rcAslList.layoutManager = GridLayoutManager(context, 3)
            bindingAsl.rcAslList.adapter = adapterAsl
                aslModelList.aslData().observe(viewLifecycleOwner, Observer {
                Log.d("null data", it.size.toString())
                adapterAsl.submitList(it)
            })
            adapterAsl.setAlertDialogListener(this)
        }catch (e: Exception){
            Log.d("Error AslButon", e.message.toString())
        }
    }

    override fun onItemAddedBill(item: String, counter: Int, sum: Double) {
        TODO("Not yet implemented")
    }
}