package com.wantobeme.lira.views

import com.wantobeme.lira.R

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
        object Katalog: Screen("katalog", R.drawable.ic_baseline_home_24,"Katalog"){
            object Search: Screen("search", R.drawable.ic_baseline_search_24,"Search")
            object DetailKatalog: Screen("detailKatalog", R.drawable.ic_baseline_home_24,"Detail Katalog")
        }
        object HistoryLoan: Screen("history_loan", R.drawable.ic_baseline_library_books_24,"History"){
            object Item: Screen("historyItem", R.drawable.ic_baseline_home_24, "History Item")
        }
        object QRScanner: Screen("qr_scanner", R.drawable.ic_baseline_qr_code_scanner_24,"QR Scanner"){
            object Koleksi: Screen("qr_buku", R.drawable.ic_baseline_qr_code_scanner_24,"Scanning")
        } //qrScanner
        object More: Screen("more", R.drawable.ic_baseline_more_horiz_24,"More")
        object Presensi: Screen("presensi", R.drawable.ic_baseline_fact_check_24,"Presensi") //listHistoryPeminjaman
        object Pelanggaran: Screen("pelanggaran", R.drawable.ic_baseline_notifications_24,"Pelanggaran")
        object NotifAnggota: Screen("notifikasiAnggota", R.drawable.ic_baseline_notifications_24,"Notifikasi")

    }
    object Petugas: Screen("nav_petugas", R.drawable.ic_baseline_account_circle_24,"Petugas"){
        object Sirkulasi: Screen("sirkulasi", R.drawable.ic_baseline_home_24, "Sirkulasi"){ //listDetailLoanAnggota
            object Loan: Screen("sirkulasiLoan", R.drawable.ic_baseline_home_24, "Sirkulasi Loan"){
                object Item: Screen("sirkulasiItem", R.drawable.ic_baseline_home_24, "Sirkulasi Item")
            }
        }
        object Pelanggaran: Screen("daftarPelanggaran", R.drawable.ic_baseline_account_circle_24, "Daftar Pelanggaran"){
            object Tambah: Screen("tambahPelanggaran", R.drawable.ic_baseline_account_circle_24, "Tambah Pelanggaran")
        }
        object Search: Screen("searchKatalogPetugas", R.drawable.ic_baseline_search_24, "Search"){
            object DetailKatalog: Screen("detailKatalog", R.drawable.ic_baseline_home_24, "Detail Katalog"){
                object Koleksi: Screen("Koleksi", R.drawable.ic_baseline_library_books_24, "Koleksi"){
                    object Tambah: Screen("tambahKoleksi", R.drawable.ic_baseline_library_books_24, "Tambah Koleksi")
                    object GenerateQR: Screen("koleksiQR", R.drawable.ic_baseline_qr_code_2_24, "Generate QR")
                }
            }
        }
        object DaftarPresensi: Screen("daftarPresensi", R.drawable.ic_baseline_fact_check_24, "Presensi"){
            object GenerateQR: Screen("PresensiQR", R.drawable.ic_baseline_qr_code_2_24, "Generate QR")
        }
        object Manage: Screen("opsiPetugas", R.drawable.ic_outline_account_circle_24, "Manage")
        object NotifikasiPetugas: Screen("notifikasiPetugas", R.drawable.ic_baseline_notifications_24,"Notifikasi")
    }

}