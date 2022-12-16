package com.wantobeme.lira.views.petugas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wantobeme.lira.model.Pelanggaran
import com.wantobeme.lira.viewmodel.petugas.PelanggaranViewModel
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun DaftarPelanggaranScreen(pelanggaranViewModel: PelanggaranViewModel, navController: NavController){

    LaunchedEffect(key1 = Unit){
        pelanggaranViewModel.getAllPelanggaran()
    }

    val allPelanggaranFlow = pelanggaranViewModel.allPelanggaranFlow.collectAsState()

    allPelanggaranFlow.value?.let {
        when(it){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(text = "Terjadi kesalahan pada sistem terkait Daftar Pelanggaran")
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
                            Text(text = "Belum ada yang Pelanggaran.")
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
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CardListPelanggaran(
    @PreviewParameter(SamplePelanggaranProvider::class) data: Pelanggaran
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "${data.collectionLoanId}/${data.collectionId}",
                style = TextStyle(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Thin
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = "Pelanggaran: ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if(data.createDate!=null){
                    Text(
                        text = "${data.createDate}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

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
                data.judulBuku,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Row{
                Text(
                    data.penulisBUku,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "(${data.tahunTerbit})",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    " / ${data.collectionId}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    "Jenis Pelanggaran",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    data.jenisPelanggaran,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    "Jumlah Denda",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    data.jumlahDenda,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Red
                )
            }
        }
    }
}

class SamplePelanggaranProvider: PreviewParameterProvider<Pelanggaran> {
    override val values = sequenceOf(
        Pelanggaran(
            "122120100001",
            "NING JAUHAROTUL WAHIDIYAH",
            "12478",
            "Kitab Desain Grafis Komplet Untuk Pemula",
            "Jubilee Enterprise",
            "2016",
            "Telat + Rusak",
            "90000",
            "16 December 2022"
        ),

        )
}