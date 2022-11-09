package com.wantobeme.lira.di

import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.repository.KatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RetrofitApi {

    @Singleton
    @Provides
    fun provideApiService(): ApiServices {

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(ApiServices::class.java)
    }
    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun provideKatalogRepository(api: ApiServices): KatalogRepository {
        return KatalogRepository(api = api)
    }
}

object Constant {

    const val BASE_URL ="http://192.168.9.212:9090/"

}