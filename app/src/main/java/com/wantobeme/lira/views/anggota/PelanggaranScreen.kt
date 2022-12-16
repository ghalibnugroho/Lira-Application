package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wantobeme.lira.viewmodel.petugas.PelanggaranViewModel
import com.wantobeme.lira.views.petugas.CardListPelanggaran
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun PelanggaranScreen(pelanggaranViewModel: PelanggaranViewModel){

    LaunchedEffect(key1 = Unit){
        val memberNo = pelanggaranViewModel.getMemberNo()
        pelanggaranViewModel.getPelanggaranAnggota(memberNo.identitas)
    }

    val pelanggaranAnggotaFlow = pelanggaranViewModel.pelanggaranAnggotaFlow.collectAsState()

    pelanggaranAnggotaFlow.value?.let {
        when(it){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(text = "Terjadi kesalahan pada sistem terkait History Pelanggaran anda")
                }
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                if(it.result.status == 1){
                    if(it.result.data == null){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(text = "Belum ada Pelanggaran.")
                        }
                    }else{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyColumn(){
                                items(it.result.data.size){ item ->
                                    CardListPelanggaran(data = it.result.data[item])
                                }
                            }
                        }
                    }
                }
                else{
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Text(text = "Belum melakukan pelanggaran")
                    }
                }
            }
        }
    }





}