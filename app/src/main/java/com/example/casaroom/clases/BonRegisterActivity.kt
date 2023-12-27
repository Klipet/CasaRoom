package com.example.casaroom.clases

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.casaroom.R
import com.example.casaroom.adapter.TabAdapter
import com.example.casaroom.alert_dialog.AlertDialogAslToBill
import com.example.casaroom.alert_dialog.AlertDialogListner
import com.example.casaroom.alert_dialog.AlertDialogPayType
import com.example.casaroom.api.API
import com.example.casaroom.api.RetrofitApi
import com.example.casaroom.constant.Constant
import com.example.casaroom.databinding.ActivityBonRegisterBinding
import com.example.casaroom.fragment.AslListBlankFragment
import com.example.casaroom.fragment.BonListFragment
import com.example.casaroom.fragment.PaymentFragment
import com.example.casaroom.modelsView.BillModel
import com.example.casaroom.modelsView.CasaModel
import com.example.casaroom.modelsView.ParentView
import com.example.casaroom.modelsView.SetingModel
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BonRegisterActivity : AppCompatActivity() {
    private lateinit var bindingBon: ActivityBonRegisterBinding
    private lateinit var vp2: ViewPager2
    private lateinit var db: DataBaseRoom
    private lateinit var folderTab: TabAdapter
    private lateinit var mvCasa: CasaModel
    private lateinit var parentList: ParentView
    private lateinit var setingModel: SetingModel
    private lateinit var sh: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingBon = ActivityBonRegisterBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_bon_register)
        setContentView(bindingBon.root)
        db = DataBaseRoom.getDB(applicationContext)
        sh = getSharedPreferences("PREFERENSES", MODE_PRIVATE)
        val handler = Handler()
        val millis = 100
        fragmentBillList()
        getCasaName()
        tabList()
        insertSeting()
        val runn = Runnable {
            payplay()
        }
        handler.postDelayed(runn, millis.toLong())
    }

    private fun fragmentBillList() {
        val fragmentMenager = supportFragmentManager
        val fragmentTransaction = fragmentMenager.beginTransaction()
        val billfragment = BonListFragment()
        fragmentTransaction.replace(R.id.viewBillList, billfragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun getCasaName(){
        mvCasa = CasaModel(db)
        GlobalScope.launch {
            val casaMane = mvCasa.getCasa().casaName
            withContext(Dispatchers.Main){
                bindingBon.tvNameCasa.text = casaMane
            }
        }
    }

    fun tabList(){
        try {
            var tabFolder = bindingBon.tabfolder
            val vpAslGrid = bindingBon.vpAsl
            parentList = ParentView(db.DaoFolder())
            vp2 = findViewById(R.id.vpAsl)
            val fragmentAsl = AslListBlankFragment()
            val folder = parentList.folder()
            folder.observe(this@BonRegisterActivity, Observer {
                runOnUiThread{
                    vp2 = findViewById(R.id.vpAsl)
                    folderTab = TabAdapter(this@BonRegisterActivity, it)
                    vp2.adapter = folderTab
                    TabLayoutMediator(tabFolder, vpAslGrid){
                            tab, position ->
                        tab.text = it[position].name
                        val bundle = Bundle()
                        bundle.putString("parentID", it[position].IDAsl)
                        fragmentAsl.arguments = bundle
                    }.attach()

                }
            })


        }catch (e: Exception){
            Log.d("Error Tab", e.message.toString())
        }
    }

    private fun insertSeting(){
        setingModel = SetingModel(db)
        val token = sh.getString(Constant.TOKEN, "non")
        CoroutineScope(Dispatchers.IO).launch {
            val casa = db.DaoCasa().getAllCasa().casaID
            val response = API.api.getSetingWP(token.toString(), casa)
            val getWorkplacesResult = response.GetWorkplaceSettingsResult
            setingModel.insertseting(getWorkplacesResult.FiscalDevice, getWorkplacesResult.PaymentTypes)
        }

    }
    fun payplay() = with (bindingBon) {
         btOplata.setOnClickListener {
          //   if(viewPayment.visibility == View.VISIBLE){
          //       val fragmentMenager = supportFragmentManager
          //       val fragmentTransaction = fragmentMenager.beginTransaction()
          //       val billfragment = PaymentFragment()
          //       fragmentTransaction.replace(R.id.viewPayment, billfragment)
          //       fragmentTransaction.addToBackStack(null)
          //       fragmentTransaction.commit()
          //   }else{
          //       viewPayment.visibility  = View.VISIBLE
          //   }

            val dialog = AlertDialogPayType(this@BonRegisterActivity)
             getBilssSum { sum ->
                 getPayType { payment ->
                     getBillAsl {
                         dialog.paiment(payment!!, sum!!, it!!)
                     }

                 }
            }
        }
    }
    private fun getBilssSum(callback: (Double?) -> Unit){
        val billSum = BillModel(db)
         billSum.getSumBill().observe(this, Observer { sum ->
             callback(sum)
        })
    }
    private fun getPayType(callback: (List<PaymentTypeDB>?) -> Unit){
        val payType = SetingModel(db)
        payType.payType().observe(this, Observer {
            callback(it)
        })
    }
    private fun getBillAsl(callback: (List<BillListDB>?) -> Unit){
        val payType = BillModel(db)
        payType.getBill().observe(this, Observer {
            callback(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        supportFragmentManager.beginTransaction()
        super.onCreate(savedInstanceState, persistentState)
    }




}

