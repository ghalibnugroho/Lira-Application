package com.wantobeme.lira.views.uiState

data class RegistrasiState(
    var email: String,
    var password: String,
    var nameLengkap: String,
    var jenisIdentitas: Int,
    var nomorIdentitas: String,
    var alamatLengkap: String,
    var jenisKelamin: Int,
    var no_hp: String,
    var institusi: String
)
