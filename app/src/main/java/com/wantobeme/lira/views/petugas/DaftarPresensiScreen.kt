package com.wantobeme.lira.views.petugas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.Presensi
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.viewmodel.petugas.DaftarPresensiViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun DaftarPresensiScreen(presensiViewModel: DaftarPresensiViewModel, navController: NavController){

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    LaunchedEffect(key1 = currentRoute == Screen.Petugas.DaftarPresensi.route){
        presensiViewModel.getAllPresensiAnggota()
    }

    val presensi = presensiViewModel.allPresensi.collectAsState()

    presensi.value?.let {
        when(it){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(text = "Terjadi kesalahan sistem pada presensi anggota")
                }
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
                        Text(text = "Belum ada yang melakukan presensi.")
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextButton(onClick = {
                            navController.navigate(Screen.Petugas.DaftarPresensi.GenerateQR.route)
                        }){
                            Text(text = "Generate",
                                style = TextStyle(
                                    fontWeight = FontWeight(400),
                                    fontSize = 20.sp
                                )
                            )
                            Icon(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .crossfade(true)
                                        .data(R.drawable.ic_baseline_qr_code_2_24)
                                        .build()
                                ),
                                contentDescription = "QR Icon"
                            )
                        }
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

@Preview(showBackground = true)
@Composable
fun CardPresensi(
    @PreviewParameter(SamplePresensiItemsProvider::class) data: Presensi
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = "Presensi: ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    data.tanggalPresensi,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                data.namaLengkap,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                data.nomorIdentitas,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

class SamplePresensiItemsProvider: PreviewParameterProvider<Presensi>{
    override val values = sequenceOf(
        Presensi(
            "Azka Muhammad",
            "35780392130",
            "2022-04-23 13:48:51"
        )
    )
}