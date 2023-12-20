package com.example.casaroom.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.casaroom.roomDB.assortiment.AsortimentDB
import com.example.casaroom.roomDB.assortiment.BarcodesDB
import com.example.casaroom.roomDB.assortiment.IsFolderDB
import com.example.casaroom.roomDB.assortiment.PromoDB
import com.example.casaroom.roomDB.bill.BillListDB
import com.example.casaroom.roomDB.casa.CasaDB
import com.example.casaroom.roomDB.converters.ConvertBarcodes
import com.example.casaroom.roomDB.converters.ConvertPromo
import com.example.casaroom.roomDB.dao_setting.DaoAssortiment
import com.example.casaroom.roomDB.dao_setting.DaoBarcodes
import com.example.casaroom.roomDB.dao_setting.DaoBillList
import com.example.casaroom.roomDB.dao_setting.DaoCasa
import com.example.casaroom.roomDB.dao_setting.DaoFolder
import com.example.casaroom.roomDB.dao_setting.DaoPromo
import com.example.casaroom.roomDB.dao_setting.DaoSetingWSP
import com.example.casaroom.roomDB.work_seting.FiscalDeviceDB
import com.example.casaroom.roomDB.work_seting.PaymentTypeDB


@Database(entities = [CasaDB::class, AsortimentDB::class, BarcodesDB::class, IsFolderDB::class, PromoDB::class,
     PaymentTypeDB::class, FiscalDeviceDB::class, BillListDB::class], version = 1, exportSchema = false)
@TypeConverters(ConvertPromo::class, ConvertBarcodes::class)
public abstract class DataBaseRoom: RoomDatabase() {

   abstract fun DaoAssortiment(): DaoAssortiment
   abstract fun DaoCasa(): DaoCasa
   abstract fun DaoCBarcodes(): DaoBarcodes
   abstract fun DaoFolder(): DaoFolder
   abstract fun DaoProm(): DaoPromo
   abstract fun DaoBillList(): DaoBillList
   abstract fun DaoSetingWSP(): DaoSetingWSP


   companion object{
       @Volatile
       private var INSTANCE: DataBaseRoom? = null
       fun getDB(context: Context): DataBaseRoom {

           return (INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   DataBaseRoom::class.java,
                   "casa"
               ).build()
               INSTANCE = instance
               instance
           }) as DataBaseRoom
       }
   }


}