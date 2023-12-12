package com.example.casaroom.clases

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.coroutineScope
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
        sh = getSharedPreferences("PREFERENSES", MODE_PRIVATE)
        val login = binding.tvLogin.text
        val password = binding.tvPassword.text
        try {
            binding.button.setOnClickListener {
                insertToken(login.toString(), password.toString())
            }
        }catch (e: NullPointerException){
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun insertToken(login: String, password: String) {
        val sheredPreference = getSharedPreferences("PREFERENSES", MODE_PRIVATE).edit()
        try {
            CoroutineScope(Dispatchers.IO).launch{
                sheredPreference.putString(Constant.LOGIN, login).apply()
                sheredPreference.putString(Constant.PASS, password).apply()
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
        val sheredPreferenceRet = getSharedPreferences("PREFERENSES", MODE_PRIVATE)
        val token = sheredPreferenceRet.getString(Constant.TOKEN, errorShared)
        if (token != errorShared){
            GlobalScope.launch {
                val casaReqest = retrofitResponse.getWorkPlace(token.toString()).GetWorkplacesResult
 //               val casa = casaReqest.body()!!.GetWorkplacesResult
                val casaArr = casaReqest.Workplaces.map { it.Name }.toTypedArray()
                val emptyTable = db.DaoCasa().getAllCasa()
                if (emptyTable == null){
                    withContext(Dispatchers.Main){
                        alertDialog(casaReqest, casaArr)
                    }
                }else{
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




}