package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.viewmodel.petugas.DaftarPresensiViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.petugas.CardPresensi
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun PresensiScreen(presensiViewModel: DaftarPresensiViewModel){

    LaunchedEffect(key1 = AnggotaActivity::class){
        val memberNo = presensiViewModel.getMemberNo()
        presensiViewModel.getPresensiAnggota(memberNo.identitas)
    }

    val presensiAnggota = presensiViewModel.presensiAnggota.collectAsState()

    presensiAnggota.value?.let {
        when(it){
            is Resource.Failure -> {
                TODO()
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
                        Text(text = "Belum melakukan presensi.")
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyColumn(){
                            items(it.result.size){items ->
                                CardPresensi(data = it.result[items])
                            }
                        }
                    }
                }
            }
        }
    }


}