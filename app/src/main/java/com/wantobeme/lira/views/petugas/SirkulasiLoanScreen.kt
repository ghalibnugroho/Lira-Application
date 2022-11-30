package com.wantobeme.lira.views.petugas

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.SirkulasiAnggota
import com.wantobeme.lira.model.SirkulasiLoan
import com.wantobeme.lira.viewmodel.ViewModelFactoryProvider
import com.wantobeme.lira.viewmodel.guest.KatalogDetailViewModel
import com.wantobeme.lira.viewmodel.petugas.SirkulasiLoanViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.navigation.PetugasNav
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import dagger.hilt.android.EntryPointAccessors

@Composable
fun provideSirkulasiLoanViewModel(memberNo: String?): SirkulasiLoanViewModel{
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).sirkulasiLoanViewModelFactory()

    return viewModel(factory = SirkulasiLoanViewModel.provideSirkulasiLoanViewModelFactory(factory, memberNo))
}

@Composable
fun SirkulasiLoanScreen(viewModel: SirkulasiLoanViewModel, navController: NavController){

    val sirkulasiLoan = viewModel.sirkulasiLoanResponse.collectAsState()

    sirkulasiLoan.value?.let {
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
                        Text(text = "Anggota belum melakukan peminjaman buku.")
                    }
                }else{
                    LazyColumn(){
                        items(it.result.size){ item ->
                            SirkulasiLoanCard(
                                sirkulasiLoan = it.result[item],
                                index = item,
                                onClick = {
                                    navController.navigate(Screen.Petugas.Sirkulasi.Loan.Item.route + "/${it.result[item].id}")
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
fun SirkulasiLoanCard(
    sirkulasiLoan: SirkulasiLoan,
    index: Int,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "${sirkulasiLoan.id}",
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                if(index==0){
                    Text(
                        text = "Latest",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.padding(20.dp, 12.dp)
            ){
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data(R.drawable.ic_baseline_library_books_24)
                            .build(),
                        filterQuality = FilterQuality.High,
                        placeholder = painterResource(id = R.drawable.ic_baseline_library_books_24),
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
                        text = "${sirkulasiLoan.tanggalPinjam}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Row() {
                        Text(
                            text = "Jumlah Buku: ",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Text(
                            text = "${sirkulasiLoan.jumlahBuku}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun prevSirkulasiLoanCard(){
    val loan  =
        SirkulasiLoan(
            id = "12391823901823",
            jumlahBuku = 2,
            tanggalPinjam = "22-12-2022"
        )
    SirkulasiLoanCard(sirkulasiLoan = loan, index = 1, onClick = {})
}

