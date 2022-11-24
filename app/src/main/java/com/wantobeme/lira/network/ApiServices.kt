package com.wantobeme.lira.network

import com.wantobeme.lira.model.*
import retrofit2.http.*


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
    ): GuestToken

    @FormUrlEncoded
    @POST("registrasi")
    suspend fun registrasi(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nama_lengkap") nameLengkap: String,
        @Field("jenis_identitas") jenisIdentitas: String,
        @Field("no_identitas") nomorIdentitas: String,
        @Field("alamat") alamatLengkap: String,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_hp") no_hp: String,
        @Field("institusi") institusi: String
    ): GuestRegistrasi
    // Petugas
    @GET("petugas/getAllAnggota")
    suspend fun getAnggota():RSirkulasiAnggota

    @GET("petugas/anggotaCollectionLoans/{memberNo}")
    suspend fun getCollectionLoansAnggota(@Path("memberNo") memberNo: String): RSirkulasiLoan

    @GET("petugas/anggotaCollectionLoanItems/{collectionLoan_id}")
    suspend fun getCollectionLoanItemsAnggota(@Path("collectionLoan_id") collectionLoan_id: String): RSirkulasiLoanItems

    @GET("petugas/koleksiKatalog/{id}")
    suspend fun getKoleksiKatalog(@Path("id") id: String): RKoleksi

    @FormUrlEncoded
    @POST("petugas/tambahKoleksi")
    suspend fun addCollection(
        @Field("katalogId") katalogId: String,
        @Field("nomorQRCode") nomorQRCode: String,
        @Field("nomorKoleksi") nomorKoleksi: String
    ): KoleksiOperation

    @DELETE("petugas/delete/koleksi/{collectionId}")
    suspend fun deleteKoleksi(@Path("collectionId") collectionId: String): KoleksiOperation

    @GET("petugas/kodeQR/{QR}")
    suspend fun getKodeQR(@Path("QR") kodeQR: String): QRCode

    @GET("petugas/daftarPresensi")
    suspend fun getAllDaftarPresensi(): RPresensi

    // Anggota
    @GET("anggota/daftarPresensi/{memberNo}")
    suspend fun getPresensiByNomorAnggota(@Path("memberNo") memberNo: String): RPresensi

}