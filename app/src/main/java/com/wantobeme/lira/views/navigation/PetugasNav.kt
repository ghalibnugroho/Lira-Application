package com.wantobeme.lira.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wantobeme.lira.views.KatalogDetailScreen
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.SearchScreen
import com.wantobeme.lira.views.petugas.*
import com.wantobeme.lira.views.provideKatalogDetailViewModel

@Composable
fun PetugasNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Petugas.Sirkulasi.route){
        composable(Screen.Petugas.Sirkulasi.route){
            SirkulasiAnggotaScreen(hiltViewModel(), navController = navController)
        }
        composable(Screen.Petugas.Sirkulasi.Loan.route + "/{nomorIdentitas}",
            arguments = listOf(navArgument(name = "nomorIdentitas"){
                type = NavType.StringType
            })
        ){ entry ->
            SirkulasiLoanScreen(
                viewModel = provideSirkulasiLoanViewModel(memberNo = entry.arguments?.getString("nomorIdentitas")),
                navController = navController
            )
        }
        composable(Screen.Petugas.Sirkulasi.Loan.Item.route + "/{collectionLoanId}",
            arguments = listOf(navArgument(name = "collectionLoanId"){
                type = NavType.StringType
            })
        ){ entry ->
            SirkulasiLoanItemsScreen(viewModel = provideSirkulasiLoanItemsViewModel(
                collectionId = entry.arguments?.getString("collectionLoanId")),
                navController = navController
            )
        }
        composable(Screen.Petugas.Search.route){
            SearchScreen(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Screen.Petugas.Search.DetailKatalog.route + "/{Id}",
            arguments = listOf(navArgument(name = "Id") {
                type = NavType.StringType
            })){ entry ->
            KatalogDetailScreen(
                viewModel = provideKatalogDetailViewModel(bookId = entry.arguments?.getString("Id")),
                navController = navController
            )
        }
        composable(Screen.Petugas.Search.DetailKatalog.Koleksi.route + "/{Id}",
            arguments = listOf(navArgument(name = "Id") {
                type = NavType.StringType
            })){ entry ->
            KoleksiScreen(
                viewModel = provideKoleksiViewModel(bookId = entry.arguments?.getString("Id")),
                navController = navController
            )
        }
        composable(Screen.Petugas.Search.DetailKatalog.Koleksi.Tambah.route + "/{Id}",
            arguments = listOf(navArgument(name = "Id") {
                type = NavType.StringType
            })){ entry ->
            TambahKoleksiScreen(
                viewModel = provideKoleksiViewModel(bookId = entry.arguments?.getString("Id")),
                navController = navController
            )
        }
        composable(Screen.Petugas.Search.DetailKatalog.Koleksi.GenerateQR.route + "/{kodeQR}",
            arguments = listOf(navArgument(name = "kodeQR") {
                type = NavType.StringType
            })){ entry ->
            QRGeneratorScreen(
                qrViewModel = provideQRViewModel(kodeQR = entry.arguments?.getString("kodeQR")),
                navController = navController
            )
        }
        composable(Screen.Petugas.DaftarPresensi.route){
            DaftarPresensiScreen(hiltViewModel(), navController)
        }
        composable(Screen.Petugas.DaftarPresensi.GenerateQR.route){
            QRGeneratorScreen(
                qrViewModel = provideQRViewModel(kodeQR = "PerpustakaanUNIRA"),
                navController = navController
            )
        }
        composable(Screen.Petugas.Manage.route){
            ManageScreen(hiltViewModel(), hiltViewModel(), navController)
        }
        composable(Screen.Petugas.Pelanggaran.route){
            DaftarPelanggaranScreen(hiltViewModel(), navController)
        }
        composable(Screen.Petugas.Pelanggaran.Tambah.route + "/{memberNo}/{loanId}/{collectionId}",
            arguments = listOf(
                navArgument(name = "memberNo") {
                    type = NavType.StringType
                },
                navArgument(name = "loanId") {
                    type = NavType.StringType
                },
                navArgument(name = "collectionId") {
                    type = NavType.StringType
                }
            )
        ){ entry ->
            TambahPelanggaranScreen(hiltViewModel(), navController)
        }
    }
}