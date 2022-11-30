package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName


data class Anggota(
    @field:SerializedName("memberNo")
    val nomorIdentitas: String,

    @field:SerializedName("fullName")
    val namaLengkap: String,

    @field: SerializedName("sexId")
    val jenisKelamin: Int,

    @field:SerializedName("address")
    val alamatLengkap: String,

    @field: SerializedName("phone")
    val no_hp: String,

    @field: SerializedName("institution")
    val institution: String,
)

// after scan
data class Loaning(

    @field:SerializedName("status")
    var status: Int,

    @field:SerializedName("catalogid")
    var katalogId: String,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("author")
    var author: String,

    @field:SerializedName("publishYear")
    var publishYear: String,

    @field:SerializedName("publisher")
    var publisher: String,

    @field:SerializedName("publishLocation")
    var publishLocation: String,

    @field:SerializedName("coverURL")
    var coverURL: String,

    @field:SerializedName("collectionid")
    var collectionId: String,

    @field:SerializedName("nomorqrcode")
    var qrcode: String,

    @field:SerializedName("callnumber")
    var callNumber: String,
)
