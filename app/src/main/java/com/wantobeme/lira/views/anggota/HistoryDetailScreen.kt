package com.wantobeme.lira.views.anggota

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.viewmodel.anggota.HistoryViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.petugas.DetailSirkulasiLoanItems
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun HistoryDetailScreen(historyViewModel: HistoryViewModel, navController: NavController){

    val current_args = navController.currentBackStackEntry?.arguments?.getString("collectionLoanId")
    var index by rememberSaveable{ mutableStateOf(0) }
    var showDialog by rememberSaveable{ mutableStateOf(false) }
    var successDialog by rememberSaveable{ mutableStateOf(false) }

    LaunchedEffect(key1 = Unit){
        historyViewModel.getHistoryLoanItems(current_args!!)
    }

    val loanItems = historyViewModel.historyLoanItems.collectAsState()
    val extendResponse = historyViewModel.extendLoanResponse.collectAsState()

    loanItems.value?.let {
        when(it){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(text = "Terjadi kesalahan pada sistem terkait History peminjaman buku.")
                }
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                if (it.result == null){
                    Text(text = "Detail peminjaman buku tidak ada.")
                }else {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            Text(
                                text = "Detail Peminjaman Buku :",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.size(25.dp))
                            LazyColumn() {
                                items(it.result.size){ item ->
                                    DetailSirkulasiLoanItems(
                                        data = it.result[item],
                                        petugas = false,
                                        finish = {},
                                        extend = {
                                            index = item
                                            showDialog = true
                                        }
                                    )
                                    if(showDialog){
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                            },
                                            title = { Text(text = "Extend Peminjaman") },
                                            text = { Text(text = "Apakah anda ingin memperpanjang durasi peminjaman? Koleksi ${it.result[index].collectionId}") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
                                                    historyViewModel.extendLoan(it.result[index].collectionLoanId, it.result[index].collectionId)
                                                },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Primary
                                                    )) {
                                                    Text(text = "ya", color = Color.White)
                                                }
                                                OutlinedButton(onClick = {
                                                    showDialog = false
                                                },
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                    )) {
                                                    Text(text = "tidak", color = Color.Red)
                                                }
                                            }
                                        )
                                    }
                                    if(successDialog){
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                                navController.navigate(Screen.Anggota.HistoryLoan.Item.route + "/${it.result[index].collectionLoanId}")
                                            },
                                            title = { Text(text = "Berhasil") },
                                            text = { Text(text = "Peminjaman anda telah diperpanjang.") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
                                                    navController.navigate(Screen.Anggota.HistoryLoan.Item.route + "/${it.result[index].collectionLoanId}")
                                                },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Primary
                                                    )) {
                                                    Text(text = "proceed", color = Color.White)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        extendResponse.value?.let {
                            when(it){
                                is Resource.Failure -> TODO()
                                Resource.Loading -> {
                                    showProgressBar()
                                }
                                is Resource.Success -> {
                                    LaunchedEffect(key1 = Unit){
                                        successDialog = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    BackHandler() {
        navController.navigate(Screen.Anggota.HistoryLoan.route)
    }
}