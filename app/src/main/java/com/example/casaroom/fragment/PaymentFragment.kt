package com.example.casaroom.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.casaroom.databinding.FragmentPaymentBinding
import com.example.casaroom.modelsView.BillModel
import com.example.casaroom.modelsView.SetingModel
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB


class PaymentFragment : Fragment() {
    private lateinit var bindungPayment: FragmentPaymentBinding
    private lateinit var payModel: SetingModel
    private lateinit var db: DataBaseRoom


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = context?.let { DataBaseRoom.getDB(it.applicationContext) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindungPayment = FragmentPaymentBinding.inflate(inflater, container,false)
        val view = bindungPayment.root
        cancelBT()
 //       payRecicler()
        paySetting()
        return view
        //return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PaymentFragment().apply {
            }
    }

    fun cancelBT() {
        bindungPayment.btCancel.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    fun paySetting() = with(bindungPayment){
        var sumBill = 0.0
        getBilssSum {
            sumBill.plus(it!!.toDouble())
            return@getBilssSum
        }
        tvSum2.text = sumBill.toString()


    }

    private fun getBillAsl(callback: (List<BillListDB>?) -> Unit){
        val payType = BillModel(db)
        payType.getBill().observe(this, Observer {
            callback(it)
        })
    }
    private fun getPayType(callback: (List<PaymentTypeDB>?) -> Unit){
        val payType = SetingModel(db)
        payType.payType().observe(viewLifecycleOwner, Observer {
            callback(it)
        })
    }
    private fun getBilssSum(callback: (Double?) -> Unit){
        val billSum = BillModel(db)
        billSum.getSumBill().observe(viewLifecycleOwner, Observer { sum ->
            callback(sum)
        })
    }
}