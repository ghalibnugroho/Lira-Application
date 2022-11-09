package com.wantobeme.lira.network

import com.wantobeme.lira.model.RKatalog
import com.wantobeme.lira.model.RKatalogDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {
    @GET("katalogs")
    suspend fun getKatalog() : RKatalog

    @GET("katalogs/{id}")
    suspend fun getDetailKatalog(@Path("id") id: String): RKatalogDetail

    @GET("katalogs/search")
    suspend fun searchKatalogs(@Query("param") param: String): RKatalog

}