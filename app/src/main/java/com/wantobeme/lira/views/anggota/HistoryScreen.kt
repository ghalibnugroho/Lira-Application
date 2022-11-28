package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.anggota.HistoryViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.petugas.SirkulasiLoanCard
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun HistoryLoanScreen(historyViewModel: HistoryViewModel, navController: NavController){

    LaunchedEffect(key1 = Unit){
        val member = historyViewModel.getMemberNo()
        historyViewModel.getHistoryLoan(member.identitas)
    }

    val historyLoan = historyViewModel.historyLoan.collectAsState()

    historyLoan.value?.let {
        when(it){
            is Resource.Failure -> {

            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                if(it.result == null){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Text(text = "melakukan peminjaman buku.")
                    }
                }else{
                    LazyColumn(){
                        items(it.result.size){ item ->
                            SirkulasiLoanCard(
                                sirkulasiLoan = it.result[item],
                                onClick = {
                                    navController.navigate(Screen.Anggota.HistoryLoan.Item.route + "/${it.result[item].id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

