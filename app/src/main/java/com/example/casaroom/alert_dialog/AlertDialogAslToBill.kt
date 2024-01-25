package com.example.casaroom.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.example.casaroom.R
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlertDialogAslToBill(private val context: Context) {
    private lateinit var db: DataBaseRoom


    val alertDialog = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_item_select, null)
    val plusButton: ImageFilterView = dialogView.findViewById(R.id.imagePlus)
    val sumPrice: TextView = dialogView.findViewById(R.id.tv_item_suma)
    val name: TextView = dialogView.findViewById(R.id.tv_Item_Name)
    val counterText: TextView = dialogView.findViewById(R.id.tvCounter)
    val minusButton: ImageFilterView = dialogView.findViewById(R.id.imageMin)
    val price: TextView = dialogView.findViewById(R.id.tv_item_price)

    fun onCloc(item: String, priceItem: Double, idAsl: String, tva: String, unit: String, priceLine: String, vatQuote: Double, context: Context) {
        db = DataBaseRoom.getDB(context)

        alertDialog.setView(dialogView)
        name.text = item
        price.text = String.format("%.2f", priceItem)
        sumPrice.text = String.format("%.2f", priceItem)
        var counter = 1.0
        counterText.text = counter.toString()
        var sum = priceItem
        plusButton.setOnClickListener {
            // Действие при нажатии на кнопку "Плюс"
            counter++
            counterText.text = counter.toString()
            sum = priceItem * counter
            sumPrice.text = "%.2f".format(sum)

        }
        minusButton.setOnClickListener {
            // Действие при нажатии на кнопку "Минус"
            if (counter > 1.0){
                counter--
                counterText.text = counter.toString()
                sum = priceItem * counter
                sumPrice.text = "%.2f".format(sum)
            }else{
                sumPrice.text = String.format("%.2f", priceItem)
            }
        }
        alertDialog.setPositiveButton("ОК") { _, _ ->
            // Действие при нажатии на положительную кнопку
            // Вы можете добавить здесь код, который нужно выполнить при нажатии на эту кнопку
            //  listner.onItemAdded(item, counter, sum)
            CoroutineScope(Dispatchers.IO).launch {
                val billList = BillListDB(
                    0,
                    aslUid = idAsl,
                    aslName = item,
                    aslPrice = priceItem,
                    aslCouner = counter,
                    aslSum = sum,
                    aslunitCode = "",
                    aslVAT = tva.toString(),
                    aslunitName = unit,
                    priceLine = priceLine,
                    codeTVA = vatQuote

                )
                db.DaoBillList().insertBillList(billList)
            }

        }
        alertDialog.setNegativeButton("Отмена") { _, _ ->
            // Действие при нажатии на отрицательную кнопку
            // Вы можете добавить здесь код, который нужно выполнить при нажатии на эту кнопку
        }
        val alertDialogCreate = alertDialog.create()
        alertDialogCreate.show()
    }

}