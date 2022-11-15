package com.wantobeme.lira.network

import com.wantobeme.lira.model.RKatalog
import com.wantobeme.lira.model.RKatalogDetail
import com.wantobeme.lira.model.User
import com.wantobeme.lira.model.UserToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {
    // Guest
    @GET("katalogs")
    suspend fun getKatalog() : RKatalog

    @GET("katalogs/{id}")
    suspend fun getDetailKatalog(@Path("id") id: String): RKatalogDetail

    @GET("katalogs/search")
    suspend fun searchKatalogs(@Query("param") param: String): RKatalog

    @FormUrlEncoded
    @POST("auth")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): UserToken

    @FormUrlEncoded
    @POST("registrasi")
    suspend fun registrasi(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nama_lengkap") nameLengkap: String,
        @Field("jenis_identitas") jenisIdentitas: Int,
        @Field("no_identitas") nomorIdentitas: String,
        @Field("alamat") alamatLengkap: String,
        @Field("jenis_kelamin") jenisKelamin: Int,
        @Field("no_hp") no_hp: String,
        @Field("institusi") institusi: String
    )
    // Petugas



}