package com.wantobeme.lira.views.petugas

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dsc.form_builder.TextFieldState
import com.wantobeme.lira.R
import com.wantobeme.lira.model.Koleksi
import com.wantobeme.lira.ui.theme.OptionColor
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.unSelectedColor
import com.wantobeme.lira.viewmodel.ViewModelFactoryProvider
import com.wantobeme.lira.viewmodel.petugas.KoleksiViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.uiState.KoleksiState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import dagger.hilt.android.EntryPointAccessors

@Composable
fun provideKoleksiViewModel(bookId: String?): KoleksiViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).koleksiViewModelFactory()

    return viewModel(factory = KoleksiViewModel.provideKoleksiViewModelFactory(factory, bookId))
}

@Composable
fun KoleksiScreen(viewModel: KoleksiViewModel, navController: NavController){

    val context = LocalContext.current

    var showDialog by rememberSaveable{ mutableStateOf(false) }
    var successDialog by rememberSaveable{ mutableStateOf(false) }
    var index by rememberSaveable{ mutableStateOf(0) }

    val koleksi = viewModel.koleksiKatalog.collectAsState()
    Log.i("Koleksi", "${koleksi}")

    val paramVM = viewModel.getVMParameter()

    val delete = viewModel.deleteKoleksiFlow.collectAsState()

    
    koleksi.value?.let {
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
                        Text(text = "Tidak ada Koleksi buku yang terdaftar.")
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(20.dp))
                        TextButton(onClick = {
                                navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.Tambah.route + "/${it.result[0].katalogId}")
                        }){
                            Text(text = "+ Tambah Unit Koleksi",
                                style = TextStyle(
                                    fontWeight = FontWeight(400),
                                    fontSize = 20.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        LazyColumn{
                            items(it.result.size){ item ->
                                KoleksiKatalogItem(
                                    koleksi = it.result[item],
                                    onLongClickCard = {
                                        index = item
                                        showDialog = true
                                    },
                                    onClickIcon = {
                                        navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.GenerateQR.route + "/${it.result[item].qrcode}")
                                    }
                                )
                                if(showDialog){
                                    // jika status tidak sedang diipinjam
                                    if(it.result[index].status.toInt() == 1){
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                            },
                                            title = { Text(text = "Delete") },
                                            text = { Text(text = "Hapus Koleksi ID ${it.result[index].collectionId}?") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
                                                },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Primary
                                                    )) {
                                                    Text(text = "cancel", color = Color.White)
                                                }
                                                OutlinedButton(onClick = {
                                                    showDialog = false
                                                    viewModel.deleteKoleksiKatalog(it.result[index].collectionId)
                                                },
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                    )) {
                                                    Text(text = "delete", color = Color.Red)
                                                }
                                            }
                                        )
                                    }else{
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                            },
                                            title = { Text(text = "Delete") },
                                            text = { Text(text = "Koleksi ID ${it.result[index].collectionId} tidak dapat dihapus! (Buku Sedang diolah/dipinjam)") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
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
                                if(successDialog){
                                    AlertDialog(
                                        onDismissRequest = {
                                            showDialog = false
                                            navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.route + "/${it.result[0].katalogId}")
                                        },
                                        title = { Text(text = "Success") },
                                        text = { Text(text = "Koleksi berhasil dihapus!") },
                                        confirmButton = {
                                            Button(onClick = {
                                                showDialog = false
                                                navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.route + "/${it.result[0].katalogId}")
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
                    delete.value?.let {
                        when(it){
                            is Resource.Failure -> {
                                TODO()
                            }
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
                BackHandler {
                    navController.navigate(Screen.Petugas.Search.DetailKatalog.route + "/${it.result[0].katalogId}")
                }
            }
        }
    }
    
}

//@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KoleksiKatalogItem(
    koleksi: Koleksi,
    onLongClickCard: () -> Unit,
    onClickIcon: () -> Unit
//    @PreviewParameter(SampleKoleksiItemsProvider::class) koleksi: Koleksi
){
    if(koleksi == null){
        Text(text = "Null Koleksi BUku")
    }
    else{
        Column() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            onLongClickCard.invoke()
                        }
                    )
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        IconButton(
                            modifier = Modifier.size(25.dp),
                            onClick = {
                                onClickIcon.invoke()
                            }
                        ){
                            Icon(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .crossfade(true)
                                        .data(R.drawable.ic_baseline_qr_code_2_24)
                                        .build()
                                ),
                                contentDescription = "QR Icon")
                        }
                        if(koleksi.status.equals("1")){
                            Icon(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .crossfade(true)
                                        .data(R.drawable.ic_baseline_check_circle_24)
                                        .build()
                                ),
                                contentDescription = "Available",
                                tint = Primary
                            )
                        }else if(koleksi.status.equals("5")){
                            Icon(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .crossfade(true)
                                        .data(R.drawable.ic_baseline_close_24)
                                        .build()
                                ),
                                contentDescription = "not Available",
                                tint = Color.Red
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text("Koleksi ID")
                        Text(text = koleksi.collectionId)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        if(koleksi.status.toInt() == 1){
                            Text("Koleksi Status")
                            Text(text = "Tersedia")
                        }else if(koleksi.status.toInt() == 5){
                            Text("Koleksi Status")
                            Text(text = "Dipinjam")
                        }else{
                            Text("Koleksi Status")
                            Text(text = "-")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Text("Nomor QR-Code")
                        Text(text = koleksi.qrcode)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text("Nomor Koleksi")
                        Text(text = koleksi.callNumber)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text("Tanggal Pengadaan")
                        Text(text = koleksi.tanggalPengadaan)
                    }
                }
            }
        }
    }
}

class SampleKoleksiItemsProvider: PreviewParameterProvider<Koleksi> {
    override val values = sequenceOf(
        Koleksi(
            "5723",
            "wqeqweqweqweqweqwe",
            "11864",
            "20210628",
            "170.1 BER e C.1",
            "1",
            "2022-03-04 00:00:00"
        )
    )
}