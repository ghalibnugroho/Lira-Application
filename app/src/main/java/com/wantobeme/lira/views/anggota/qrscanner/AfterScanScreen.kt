package com.wantobeme.lira.views.anggota.qrscanner

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.petugas.KoleksiViewModel
import com.wantobeme.lira.viewmodel.petugas.QRViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun AfterScanScreen(qrScanViewModel: QRScanViewModel, navController: NavController){
    val currentArgs = navController.currentBackStackEntry?.arguments?.getString("kodeQR")
    Log.i("currentArgs AfterScan","${currentArgs}")
//    Column() {
//        Text("AfterScanScreen")
//        Text("${currentArgs}")
//    }
    LaunchedEffect(key1 = ScannerActivity::class){
        qrScanViewModel.qrCheck(currentArgs)
    }
    val qrScan = qrScanViewModel.qrScan.collectAsState()

    qrScan.value?.let {
        when(it){
            is Resource.Failure -> {
                TODO()
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {

            }
        }
    }



    BackHandler {
        navController.navigate(Screen.Anggota.QRScanner.route)
    }
}
