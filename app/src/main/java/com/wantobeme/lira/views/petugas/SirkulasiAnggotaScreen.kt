package com.wantobeme.lira.views.petugas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.SirkulasiAnggota
import com.wantobeme.lira.viewmodel.petugas.DashboardViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun SirkulasiAnggotaScreen(dashboardViewModel: DashboardViewModel, navController: NavController){
    
    
//    Text(text = "Petugas - Dashboard Screen")

    val anggota = dashboardViewModel.anggotaResponse.collectAsState()

    anggota.value?.let {
        when(it){
            is Resource.Failure -> {
                Text(text = "${it.exception.message}")
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
                        Text(text = "Tidak ada anggota yang terdaftar.")
                    }
                }else{
                    LazyColumn(){
                        items(it.result.size){ item ->
                            SirkulasiAnggotaItem(
                                sirkulasiAnggota = it.result[item],
                                onClick = {
                                    navController.navigate(Screen.Petugas.Sirkulasi.Loan.route + "/${it.result[item].nomorIdentitas}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SirkulasiAnggotaItem(
    sirkulasiAnggota: SirkulasiAnggota,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier.padding(20.dp, 12.dp)
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(R.drawable.ic_baseline_account_circle_24)
                        .build(),
                    filterQuality = FilterQuality.High,
                    placeholder = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(55.dp)
                    .height(55.dp)
                    .clip(shape = RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${sirkulasiAnggota.namaLengkap}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "${sirkulasiAnggota.nomorIdentitas}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevSirkulasiAnggotaItem(){
    val anggota  =
        SirkulasiAnggota(
        namaLengkap = "Muhammad Ghalib Nugroho",
        nomorIdentitas = "165150201111111"
    )
    SirkulasiAnggotaItem(sirkulasiAnggota = anggota, onClick = {})
}