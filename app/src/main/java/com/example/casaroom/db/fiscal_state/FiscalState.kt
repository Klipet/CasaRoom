package com.example.casaroom.db.fiscal_state

data class FiscalState(
    val Status: String,
    val deviceId: String,
    val recipesWithoutMev: Int,
    val tva: List<Tva>
)