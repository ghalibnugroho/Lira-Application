package com.wantobeme.lira.views.petugas

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wantobeme.lira.viewmodel.petugas.DaftarPresensiViewModel
import com.wantobeme.lira.views.Screen

@Composable
fun DaftarPresensiScreen(presensiViewModel: DaftarPresensiViewModel, navController: NavController){

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    LaunchedEffect(key1 = currentRoute == Screen.Petugas.DaftarPresensi.route){
        presensiViewModel.getAllPresensiAnggota()
    }

    Text(text = "Presensi Screen")


}