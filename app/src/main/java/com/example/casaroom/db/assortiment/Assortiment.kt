package com.example.casaroom.db.assortiment

data class Assortiment(
    val Assortments: List<Assortment>,
    val ErrorCode: Int,
    val ErrorText: Any,
    val QuickGroups: Any
)
