package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.anggota.HistoryViewModel
import com.wantobeme.lira.views.petugas.DetailSirkulasiLoanItems
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun HistoryDetailScreen(historyViewModel: HistoryViewModel, navController: NavController){

    val current_args = navController.currentBackStackEntry?.arguments?.getString("collectionLoanId")

    LaunchedEffect(key1 = Unit){
        historyViewModel.getHistoryLoanItems(current_args!!)
    }

    val loanItems = historyViewModel.historyLoanItems.collectAsState()

    loanItems.value?.let {
        when(it){
            is Resource.Failure -> {

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

                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}