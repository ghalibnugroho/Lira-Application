package com.wantobeme.lira.views.anggota.qrscanner

import android.view.View
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.ScannerScreen

@Composable
fun QRNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Anggota.QRScanner.route){
        composable(Screen.Anggota.QRScanner.route){
            ScannerScreen(navController)
        }
        composable(Screen.Anggota.QRScanner.Koleksi.route + "/{kodeQR}",
            arguments = listOf(navArgument(name = "kodeQR") {
                type = NavType.StringType
            })){ entry ->
            AfterScanScreen(hiltViewModel(), navController = navController)
        }
    }
}