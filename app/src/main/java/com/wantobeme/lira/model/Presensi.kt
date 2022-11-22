package com.wantobeme.lira.model

data class RPresensi(
    val title: String,
    val status: String,
    val message: String,
    val data: List<Presensi>
)

data class Presensi(
    val namaLengkap : String,
    val nomorIdentitas: String,
    val tanggalPresensi: String
)