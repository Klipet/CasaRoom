package com.example.casaroom.api

import com.example.casaroom.db.assortiment.Assortiment
import com.example.casaroom.db.post_fiscal_service.RegisterFiscalReceipt
import com.example.casaroom.db.seting_workspace.SettingWorkSpace
import com.example.casaroom.db.token.Token
import com.example.casaroom.db.workspace.WorkSpace
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/epos/json/AuthentificateUser")
    fun getTokenUser(@Query("userLogin")long: String, @Query("userPass")password: String ): Call<Token>


    @GET("/epos/json/GetWorkPlaces")
    suspend fun getWorkPlace(@Query("Token")token: String): WorkSpace

    @GET("/epos/json/GetAssortmentList")
    suspend fun getAslWP(@Query("Token")token: String, @Query("WorkplaceId")casa: String): Assortiment

    @GET("/epos/json/GetWorkPlaces")
    fun getPlace(@Query("Token")token: String): Call<WorkSpace>
    @GET("/epos/json/GetWorkplaceSettings")
    suspend fun getSetingWP(@Query("Token")token: String, @Query("WorkplaceId")casa: String): SettingWorkSpace


}
