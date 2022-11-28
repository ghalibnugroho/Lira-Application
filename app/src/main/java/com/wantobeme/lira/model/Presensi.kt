package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName


// Response PResensi
data class RPresensi(
    val title: String,
    val status: String,
    val message: String,
    val data: List<Presensi>
)
// Both
data class Presensi(

    @field:SerializedName("memberNo")
    val nomorIdentitas: String,

    @field:SerializedName("fullName")
    val namaLengkap: String,

    @field:SerializedName("createdate")
    val tanggalPresensi: String
)