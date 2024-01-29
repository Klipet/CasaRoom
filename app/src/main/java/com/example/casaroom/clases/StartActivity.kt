package com.example.casaroom.clases

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.casaroom.alert_dialog.OkDialog
import com.example.casaroom.api.API
import com.example.casaroom.constant.Constant
import com.example.casaroom.databinding.ActivityStartBinding
import com.example.casaroom.db.workspace.GetWorkplacesResult
import com.example.casaroom.modelsView.AslModel
import com.example.casaroom.modelsView.CasaModel
import com.example.casaroom.roomDB.DataBaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    val retrofitResponse = API.api
    private lateinit var db: DataBaseRoom
    private val errorShared = "non"
    private lateinit var sh: SharedPreferences
    private lateinit var vmCasa: CasaModel
    private lateinit var vmAssortiment: AslModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setContentView(R.layout.activity_start)
        db = DataBaseRoom.getDB(applicationContext)
        sh = getSharedPreferences("PREFERENSES", Context.MODE_PRIVATE)
        val login = binding.tvLogin.text.toString()
        val password = binding.tvPassword.text.toString()
        binding.button.setOnClickListener {
            try {
                insertToken(login.toString(), password.toString())
                observLoadingState()
            }catch (e: NullPointerException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }

    fun insertToken(login: String, password: String) {
        val sheredPreference = getSharedPreferences("PREFERENSES", Context.MODE_PRIVATE).edit()
        try {
            CoroutineScope(Dispatchers.IO).launch{
                sheredPreference.putString(Constant.LOGIN, login.toString()).apply()
                sheredPreference.putString(Constant.PASS, password.toString()).apply()
                val getToken = retrofitResponse.getTokenUser(login, password).execute()
                if (getToken.isSuccessful){
                    val tokenUser = getToken.body()?.AuthentificateUserResult?.Token
                    sheredPreference.putString(Constant.TOKEN, tokenUser).apply()
                    getCashWork()
                } else {
                    val errormesage = getToken.errorBody().toString()
                    runOnUiThread {
                        Toast.makeText(applicationContext, errormesage, Toast.LENGTH_LONG )
                    }
                }
            }

        }catch (e: NullPointerException){
            Log.d("Null Point Exeption", e.toString())
        }catch (e: Exception){
            Log.d("Exeption insert user", e.toString())
        }finally{

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getCashWork(){
        val sheredPreferenceRet = getSharedPreferences("PREFERENSES", Context.MODE_PRIVATE)
        val token = sheredPreferenceRet.getString(Constant.TOKEN, errorShared).toString()
        if (token != errorShared){
            GlobalScope.launch {
                val casaReqest = retrofitResponse.getWorkPlace(token.toString()).GetWorkplacesResult
                val casaArr = casaReqest.Workplaces.map { it.Name }.toTypedArray()
                val emptyTable = db.DaoCasa().getAllCasa()
                if (emptyTable == null){
                    withContext(Dispatchers.Main){
                        alertDialog(casaReqest, casaArr)
                    }
                }else{
                    getUser()
                    insertAslToDB()

                }

            }

        }

    }
    fun alertDialog(casa: GetWorkplacesResult, casaArr: Array<String>) : AlertDialog.Builder {
        vmCasa = CasaModel(db)
        val builder = AlertDialog.Builder(this@StartActivity)
        builder.setTitle("Выберите Кассу")
        builder.setItems(casaArr) { dialog, switc ->
            val casaName = casaArr[switc]
            val casaID = casa.Workplaces.get(switc)?.ID.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    getUser()
                    vmCasa.casaToDB(casaID, casaName)
                    delay(1000)
                    insertAslToDB()
                }catch (e: NullPointerException){
                    Log.d("DBCasa is null", e.message.toString())
                }
            }
        }.show()
        return builder
    }

    suspend fun insertAslToDB(){
        var dataAveibl = false
        vmAssortiment = AslModel(db, this)
        val token =  sh.getString(Constant.TOKEN, errorShared)
        while (!dataAveibl){
            val casa = db.DaoCasa().getAllCasa()
            dataAveibl = casa != null && casa.casaID.isNotEmpty()
            val casaID = casa.casaID
            GlobalScope.launch {
                val aslResponse = API.api.getAslWP(token.toString(), casaID).Assortments
                vmAssortiment.aslInsert(aslResponse)
            }
            if (!dataAveibl){
                delay(1000)
            }
        }


    }

     suspend fun getUser(){
         withContext(Dispatchers.IO) {
             try {
                 val token = sh.getString(Constant.TOKEN, errorShared)
                 val login = sh.getString(Constant.LOGIN, errorShared)
                 val casa = db.DaoCasa().getAllCasa().casaID
                 val getUserResault = API.api.getUser(token.toString(), casa)
                 val response = getUserResault.GetUsersListResult
                 response.Users.find { it.UserName == login.toString()}?.let { userName ->
                     withContext(Dispatchers.Main) {
                         OkDialog(this@StartActivity).show()
                     }
                 }
             } catch (e: Exception) {
                 Log.d("Error User", e.message.toString())

             }
         }
    }
    private fun observLoadingState(){
        vmAssortiment = AslModel(db, this)
        binding.pbStart.visibility = View.GONE
        binding.tvLogin.isEnabled = false
        binding.tvPassword.isEnabled = false
        binding.button.isEnabled = false
        vmAssortiment.loadingState.observe(this, Observer {isLoading ->
            if (isLoading) {
                // Показать ProgressBar
                binding.pbStart.visibility = View.GONE
            } else {
                // Скрыть ProgressBar
                binding.pbStart.visibility = View.GONE
            }

        })
    }
}