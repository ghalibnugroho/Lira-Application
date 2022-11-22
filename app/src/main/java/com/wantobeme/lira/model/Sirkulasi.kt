package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName

// petugas

/*  Data yang akan ditampilkan pada DashboardScreen  */
data class RSirkulasiAnggota(
    val title: String,
    val status: String,
    val message: String,
    val data: List<SirkulasiAnggota>
)
data class RSirkulasiLoan(
    val title: String,
    val status: String,
    val message: String,
    val data: List<SirkulasiLoan>
)

data class RSirkulasiLoanItems(
    val title: String,
    val status: String,
    val message: String,
    val data: List<SirkulasiLoanItems>
)

data class SirkulasiAnggota(

    @field:SerializedName("memberNo")
    val nomorIdentitas: String,

    @field:SerializedName("fullName")
    val namaLengkap: String
)

data class SirkulasiLoan(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("createdate")
    val tanggalPinjam: String,

    @field:SerializedName("collectioncount")
    val jumlahBuku: Int
)

data class SirkulasiLoanItems(

    @field:SerializedName("fullname")
    val namaLengkap: String,

    @field:SerializedName("memberno")
    val nomorIdentitas: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val noHp: String,

    @field:SerializedName("collectionloanid")
    val collectionLoanId: String,

    @field:SerializedName("loandate")
    val tanggalPinjam: String,

    @field:SerializedName("duedate")
    val tanggalBatasPinjam: String,

    @field:SerializedName("actualreturn")
    val tanggalDikembalikan: String,

    @field:SerializedName("loanstatus")
    val status: String,

    @field:SerializedName("catalogsid")
    val catalogsId: String,

    @field:SerializedName("collectionid")
    val collectionId: String,

    @field:SerializedName("nomorbarcode")
    val nomorBarcode: String,

    @field:SerializedName("title")
    var judul: String,

    @field:SerializedName("author")
    val penulis: String,

    @field:SerializedName("publishyear")
    val tahunTerbit: String,

    @field:SerializedName("coverURL")
    val coverURL: String,
)
