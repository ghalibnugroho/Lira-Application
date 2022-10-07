package com.wantobeme.lira.navigation

import com.wantobeme.lira.R

sealed class Screen (var route: String, var icon: Int, var title: String ){
    object Katalog: Screen("Katalog", R.drawable.ic_baseline_home_24, "Home")
    object DetailKatalog: Screen("DetailKatalog/{id}", R.drawable.ic_baseline_home_24, "Detail Katalog")
    object Search: Screen("Search", R.drawable.ic_baseline_search_24, "Search")
    object Login: Screen ("Login", R.drawable.ic_baseline_account_circle_24, "Login")
    object Register: Screen("Register", R.drawable.ic_baseline_account_circle_24, "Register")
    object Dashboard: Screen("Dashboard", R.drawable.ic_baseline_home_24, "Dashboard")       //listAnggota
    object SirkulasiP: Screen("SirkulasiP", R.drawable.ic_baseline_library_books_24, "SirkulasiP")     //litLoan
}