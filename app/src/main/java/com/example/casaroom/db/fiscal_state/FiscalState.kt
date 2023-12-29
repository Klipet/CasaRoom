package com.example.casaroom.db.fiscal_state

data class FiscalState(
    val CurrentReport24HoursExpired: Boolean,
    val CurrentReportDate: String,
    val CurrentReportNumber: Int,
    val FirstNotSyncedTransactionDate: Any,
    val FirstNotSyncedTransactionNumber: Any,
    val FiscalMemorySerialNumber: String,
    val LastTransactionDate: String,
    val LastTransactionMEVID: String,
    val LastTransactionNumber: Int,
    val OfflineHoursLimit: Int,
    val OfflineModeEnabled: Boolean,
    val ServiceDatabaseCorrupted: Boolean,
    val ServiceDateTime: String,
    val ServiceDatetimeInvalid: Boolean,
    val ServiceMEVSyncError: Boolean,
    val ServiceRegistrationInvalid: Boolean,
    val ServiceSerialNumber: String,
    val ServiceSignatureInvalid: Boolean
)