package com.wantobeme.lira.network

import android.util.Log
import com.wantobeme.lira.model.KatalogCover
import com.wantobeme.lira.model.RKatalog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetroAPI {
    @GET("katalogs")
    suspend fun getKatalog() : RKatalog

    @GET("katalogs/all")
    suspend fun getAllKatalog() : RKatalog


    companion object {
        var apiService: RetroAPI? = null
        var logging = HttpLoggingInterceptor()

        fun getInstance() : RetroAPI {
            // build logging for monitoring data from response
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder().addInterceptor(logging)
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://192.168.108.212:9090/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build().create(RetroAPI::class.java)
                Log.d("RetroAPI","Retrofit builder Success")
            }
            return apiService!!
        }
    }
}