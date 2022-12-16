package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName

// Response
data class RPelanggaran(
    var title: String,
    var status: Int,
    var message: String,
    var data: List<Pelanggaran>
)

// Fetch
data class Pelanggaran(

    @field: SerializedName("collectionloanId")
    var collectionLoanId: String,

    @field: SerializedName("fullName")
    var namaLengkap: String,

    @field: SerializedName("collectionId")
    var collectionId: String,

    @field: SerializedName("title")
    var judulBuku: String,

    @field: SerializedName("author")
    var penulisBUku: String,

    @field: SerializedName("publishyear")
    var tahunTerbit: String,

    @field: SerializedName("jenispelanggaran")
    var jenisPelanggaran: String,

    @field: SerializedName("jumlahdenda")
    var jumlahDenda: String,

    @field: SerializedName("createDate")
    var createDate: String? = null
)
