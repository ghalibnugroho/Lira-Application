package com.wantobeme.lira.network

import com.wantobeme.lira.model.RKatalog
import com.wantobeme.lira.model.RKatalogDetail
import retrofit2.http.GET


interface ApiServices {
    @GET("katalogs")
    suspend fun getKatalog() : RKatalog

    @GET("katalogs/all")
    suspend fun getAllKatalog() : RKatalog

    @GET("katalogs/{id}")
    suspend fun getDetailKatalog(id: String): RKatalogDetail
}