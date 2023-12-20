package com.example.casaroom.alert_dialog

import android.app.AlertDialog
import android.content.Context
import com.example.casaroom.clases.BonRegisterActivity
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB


class AlertDialogPayType(private val activity: BonRegisterActivity){
    val alertDialog = AlertDialog.Builder(activity)
    fun paiment(paymentType: List<PaymentTypeDB>){
        val payTupes = paymentType.map { it.Name }.toTypedArray()
        alertDialog.setTitle("типы оплаты")
        alertDialog.setNegativeButton("Отмена"){ dialog, with ->
        }
        alertDialog.setItems(payTupes){ dialog, with ->
            val selectedPayType = paymentType[with]
        }

        val dialog =  alertDialog.create()
        dialog.show()




    }
}