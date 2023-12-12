package com.example.casaroom.clases

import android.app.Application
import com.example.casaroom.roomDB.DataBaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AppCasa: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    val dataBase by lazy { DataBaseRoom.getDB(this) }
    val repository by lazy { dataBase.DaoCasa() }
}