package com.wantobeme.lira.views.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.PresensiScreen

@Composable
fun AnggotaNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Anggota.Presensi.route){
        composable(Screen.Anggota.Presensi.route){
            PresensiScreen()
        }
    }
}

