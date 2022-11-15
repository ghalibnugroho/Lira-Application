package com.wantobeme.lira.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wantobeme.lira.views.KatalogScreen
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.petugas.SirkulasiScreen

@Composable
fun PetugasNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Petugas.Sirkulasi.route){
        composable(Screen.Petugas.Sirkulasi.route){
            SirkulasiScreen()
        }
    }
}