package com.wantobeme.lira.di

import android.content.Context
import com.wantobeme.lira.LiraApp
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.repository.KatalogRepository
import com.wantobeme.lira.repository.PresensiRepository
import com.wantobeme.lira.repository.SirkulasiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


object Constant {
    const val BASE_URL ="http://192.168.134.212:9090/"
}

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

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):   LiraApp{
        return app as LiraApp
    }

    @Provides
    fun provideKatalogRepository(api: ApiServices): KatalogRepository {
        return KatalogRepository(api = api)
    }

    @Provides
    fun provideAuthRepository(
        api: ApiServices,
        @ApplicationContext context: Context
    ): AuthRepository{
        return AuthRepository(api = api, context = context)
    }

    @Provides
    fun provideSirkulasiRepository(
        api: ApiServices
    ): SirkulasiRepository{
        return SirkulasiRepository(api = api)
    }

    @Provides
    fun providePresensiRepository(
        api: ApiServices
    ): PresensiRepository{
        return PresensiRepository(api = api)
    }
}