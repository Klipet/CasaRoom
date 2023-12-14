package com.example.casaroom.clases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.casaroom.R
import com.example.casaroom.adapter.TabAdapter
import com.example.casaroom.databinding.ActivityBonRegisterBinding
import com.example.casaroom.fragment.AslListBlankFragment
import com.example.casaroom.modelsView.CasaModel
import com.example.casaroom.modelsView.ParentView
import com.example.casaroom.roomDB.DataBaseRoom
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BonRegisterActivity : AppCompatActivity() {
    private lateinit var bindingBon: ActivityBonRegisterBinding
    private lateinit var vp2: ViewPager2
    private lateinit var db: DataBaseRoom
    private lateinit var folderTab: TabAdapter
    private lateinit var mvCasa: CasaModel
    private lateinit var parentList: ParentView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingBon = ActivityBonRegisterBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_bon_register)
        setContentView(bindingBon.root)
        db = DataBaseRoom.getDB(applicationContext)
        getCasaName()
        tabList()
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
                        bundle.putString("parentID", it[position].name)
                        fragmentAsl.arguments = bundle
                    }.attach()

                }
            })


        }catch (e: Exception){
            Log.d("Error Tab", e.message.toString())
        }
    }

}