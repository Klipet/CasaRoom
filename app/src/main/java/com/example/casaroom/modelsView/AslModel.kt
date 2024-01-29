package com.example.casaroom.modelsView

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.casaroom.clases.BonRegisterActivity
import com.example.casaroom.db.assortiment.Assortment
import com.example.casaroom.roomDB.DataBaseRoom
import com.example.casaroom.roomDB.assortiment.AsortimentDB
import com.example.casaroom.roomDB.assortiment.BarcodesDB
import com.example.casaroom.roomDB.assortiment.IsFolderDB
import com.example.casaroom.roomDB.assortiment.PromoDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class AslModel(private val db: DataBaseRoom, private val context: Context):ViewModel() {
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState


    fun aslInsert(aslList: List<Assortment>){
        GlobalScope.launch {
            try {
                val barcodes = aslList?.map {
                    BarcodesDB(0, it.ID, it.Barcodes)
                }
                barcodes?.let { db.DaoCBarcodes().insertBarcodes(it) }
            } catch (e: Exception) {
                Log.d("Error Barcode Insert", e.message.toString())
            }
            try {
                val isFolder = aslList.filter {
                    it.IsFolder == true
                }.map {
                    IsFolderDB(it.ID, it.IsFolder, it.ParentID, it.Name)
                }
                db.DaoFolder().insertFolder(isFolder)
            } catch (e: Exception) {
                Log.d("Error folder Insert", e.message.toString())
            }
            try {
                _loadingState.postValue(false)
                val asortimentList = aslList.map {
                    val promo = it?.Promotions?.map { promo ->
                        PromoDB(
                            0,
                            AllowDiscount = promo.AllowDiscount,
                            AslID = promo.ID,
                            EndDate = promo.EndDate,
                            IDPromo = promo.ID,
                            Price = promo.Price,
                            StartDate = promo.StartDate,
                            TimeBegin = promo.TimeBegin,
                            TimeEnd = promo.TimeEnd
                        )
                    }
                    promo?.let { promo -> db.DaoProm().insertPromo(promo) }
                    AsortimentDB(
                        AllowDiscounts = it.AllowDiscounts,
                        AllowNonInteger = it.AllowNonInteger,
                        Code = it.Code,
                        EnableSaleTimeRange = it.EnableSaleTimeRange,
                        ID = it.ID,
                        IsFolder = it.IsFolder,
                        parentID = it.ParentID,
                        Marking = it.Marking,
                        Name = it.Name,
                        Barcode = it.Barcodes,
                        Price = it.Price,
                        PriceLineEndDate = it.PriceLineStartDate,
                        PriceLineId = it.PriceLineId,
                        PriceLineStartDate = it.PriceLineStartDate,
                        Promo = promo,
                        ShortName = it.ShortName,
                        StockBalance = it.StockBalance,
                        StockBalanceDate = it.StockBalanceDate,
                        Unit = it.Unit,
                        VAT = it.VAT,
                        VATQuote = it.VATQuote,
                        WeightSale = it.WeightSale
                    )
                }
                db.DaoAssortiment().insertAsl(asortimentList)
                val intent = Intent(context, BonRegisterActivity::class.java)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.d("ErrorAsortimentInsert and promo", e.message.toString())
            } finally {
                _loadingState.postValue(false)
            }
        }
    }
}