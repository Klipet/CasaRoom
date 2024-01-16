package com.example.casaroom.api

import com.example.casaroom.db.fiscal_state.FiscalState
import com.example.casaroom.db.post_fiscal_service.RegisterFiscalReceipt
import com.example.casaroom.db.post_fiscal_service.ReportRegister
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApiFiscal {
    @POST("json/RegisterFiscalReceipt")
    fun registerFiscalRecept(@Body fiscalRecept: RegisterFiscalReceipt): Call<RegisterFiscalReceipt>

    @GET("json/GetServiceState")
    suspend fun getState(): FiscalState

    @POST("json/RegisterReport")
    fun registerReport(@Body repostRegister: ReportRegister): Call<ReportRegister>
}