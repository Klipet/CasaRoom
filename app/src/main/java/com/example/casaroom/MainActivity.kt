package com.example.casaroom

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.casaroom.api.ApiFiscal
import com.example.casaroom.clases.StartActivity
import com.example.casaroom.db.fiscal_state.FiscalState
import com.example.casaroom.db.post_fiscal_service.ReportRegister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

class MainActivity : AppCompatActivity() {
    private val statusOk = "Ok"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    //    val status = getStatus()
    //    val handler = Handler()
    //    val millis = 2000
    //    val runnable = Runnable {
    //        if (status.CurrentReport24HoursExpired == true){
    //            Toast.makeText(baseContext, "Raport Z", Toast.LENGTH_LONG).show()
    //
    //        }
    //        val intent = Intent(this, StartActivity::class.java)
    //        startActivity(inten
    //        finish
    //    }
    //    handler.postDelayed(runnable, millis.toLong())

        CoroutineScope(Dispatchers.IO).launch {
            if (getStatus() == statusOk) {
                dialogZRaport()
                Toast.makeText(baseContext, "Raport Z", Toast.LENGTH_LONG).show()
            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    val handler = Handler()
                    val millis = 2000
                    val runnable = Runnable {
                        val intent = Intent(this@MainActivity, StartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    handler.postDelayed(runnable, millis.toLong())
                }
            }
        }

    }

    suspend fun getStatus(): String{
        var status = ""
        try {
            val apiStatus = ApiFiscal.api.getState()
            status = apiStatus.Status
        }catch (e: ConnectException){
            Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
        }finally {
          return status!!
        }


    }
    private fun dialogZRaport(){
        val dialog = AlertDialog.Builder(baseContext)
        dialog.setTitle("Z отчет не распечатался")
        dialog.setPositiveButton("Печать отчета Z"){ _, _ ->
                sendReport()

        }
        dialog.setNegativeButton("отмена"){_, _ ->


        }
    }
    fun sendReport(){
        val report = ReportRegister(
            CloseReport = true,
            ForcePrint = false,
            PrinterURI = ""

        )
        val postFiscaReport = ApiFiscal.api.registerReport(
            report
        )
         val response = postFiscaReport.execute()
        if (response.isSuccessful){
            CoroutineScope(Dispatchers.IO).launch {
                val status = getStatus()
                val handler = Handler()
                val millis = 2000
                CoroutineScope(Dispatchers.Main).launch {
                    val runnable = Runnable {
                        if (status == statusOk) {

                            Toast.makeText(baseContext, "Raport Z", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                    handler.postDelayed(runnable, millis.toLong())
                }
            }

        } else{
            Toast.makeText(baseContext, response.message(), Toast.LENGTH_LONG).show()
        }
    }
}