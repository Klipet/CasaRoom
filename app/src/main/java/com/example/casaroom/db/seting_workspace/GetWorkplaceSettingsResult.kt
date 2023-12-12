package com.example.casaroom.db.seting_workspace

data class GetWorkplaceSettingsResult(
    val ErrorCode: Int,
    val ErrorText: Any,
    val FiscalDevice: FiscalDevice,
    val PaymentTypes: List<PaymentType>
)
