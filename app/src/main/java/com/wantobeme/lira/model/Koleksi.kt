package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName


// Response Koleksi
data class RKoleksi(
    val title: String,
    val status: String,
    val message: String,
    val data: List<Koleksi>
)
// Petugas Page
data class Koleksi(

    @field:SerializedName("catalogid")
    var katalogId: String,

    @field:SerializedName("title")
    var judul: String,

    @field:SerializedName("collectionid")
    var collectionId: String,

    @field:SerializedName("nomorqrcode")
    var qrcode: String,

    @field:SerializedName("callnumber")
    var callNumber: String,

    @field:SerializedName("statusid")
    var status: String,

    @field:SerializedName("tanggalPengadaan")
    var tanggalPengadaan: String
)

data class KoleksiOperation(
    val status: Int,
    val message: String
)

data class QRCode(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("kodeQR")
    val qrcode: String
)