package com.wantobeme.lira.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wantobeme.lira.views.*
import com.wantobeme.lira.views.anggota.*

@Composable
fun AnggotaNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Anggota.Katalog.route){
        composable(Screen.Anggota.Katalog.route){
            HomeScreen(hiltViewModel(), navController)
        }
        composable(Screen.Anggota.Katalog.Search.route){
            SearchScreen(hiltViewModel(), navController)
        }
        composable(Screen.Anggota.Katalog.DetailKatalog.route + "/{Id}",
            arguments = listOf(navArgument(name = "Id") {
                type = NavType.StringType
            })){ entry ->
            KatalogDetailScreen(
                viewModel = provideKatalogDetailViewModel(bookId = entry.arguments?.getString("Id")),
                navController = navController
            )
        }
        composable(Screen.Anggota.HistoryLoan.route){
            HistoryLoanScreen(hiltViewModel(), navController)
        }
        composable(Screen.Anggota.HistoryLoan.Item.route + "/{collectionLoanId}",
            arguments = listOf(navArgument(name = "collectionLoanId"){
                type = NavType.StringType
            })
        ){ entry ->
//            HistoryDetailScreen(provideHistoryLoanItemsViewModel(collectionId = entry.arguments?.getString("collectionLoanId")))
            HistoryDetailScreen(hiltViewModel(), navController)
        }
        composable(Screen.Anggota.More.route){
            MoreScreen(hiltViewModel(), navController)
        }
        composable(Screen.Anggota.Presensi.route){
            PresensiScreen(hiltViewModel())
        }
        composable(Screen.Anggota.NotifAnggota.route){
            NotifikasiAnggotaScreen()
        }
    }
}

