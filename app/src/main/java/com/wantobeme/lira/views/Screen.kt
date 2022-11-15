package com.wantobeme.lira.views

import androidx.compose.ui.graphics.vector.ImageVector
import com.wantobeme.lira.R
import com.wantobeme.lira.model.KatalogDetail

sealed class Screen (var route: String, var icon: Int, var title: String ){
    object Auth: Screen("nav_auth", R.drawable.ic_baseline_account_circle_24,"Auth"){
        object Login: Screen("login", R.drawable.ic_baseline_account_circle_24, "Login")
        object Registrasi: Screen("registrasi", R.drawable.ic_baseline_account_circle_24, "Registrasi")
        object LupaPassword: Screen("lupa_password", R.drawable.ic_baseline_account_circle_24, "Lupa Password")
    }
    object Katalog: Screen("nav_katalog", R.drawable.ic_baseline_home_24, "Katalog"){
        object Search: Screen("search_katalog", R.drawable.ic_baseline_search_24, "Search")
        object DetailKatalog: Screen("detailKatalog", R.drawable.ic_baseline_home_24, "Detail Katalog")
    }
    object Anggota: Screen("nav_anggota", R.drawable.ic_baseline_account_circle_24,"Anggota"){
        object Presensi: Screen("presensi", R.drawable.ic_baseline_fact_check_24,"Presensi") //listHistoryPeminjaman
        object HistoryLoan: Screen("history_loan", R.drawable.ic_baseline_library_books_24,"History Peminjaman") //listHistoryPeminjaman
        object QRScanner: Screen("qr_scanner", R.drawable.ic_baseline_qr_code_scanner_24,"QR Scanner"){

        } //qrScanner
        object NotifAnggota: Screen("notifikasi_anggota", R.drawable.ic_baseline_notifications_24,"Notifikasi")
    }
    object Petugas: Screen("nav_petugas", R.drawable.ic_baseline_account_circle_24,"Anggota"){
        object Sirkulasi: Screen("sirkulasi", R.drawable.ic_baseline_library_books_24, "Sirkulasi"){ //listDetailLoanAnggota
            object SirkulasiItem: Screen("sirkulasi_item", R.drawable.ic_baseline_home_24, "Sirkulasi Item")
        }
        object DaftarPresensi: Screen("daftar_presensi", R.drawable.ic_baseline_fact_check_24, "Daftar Presensi")//listPresensi

        object NotifPetugas: Screen("notifikasi_petugas", R.drawable.ic_baseline_notifications_24,"Notifikasi")
    }

}