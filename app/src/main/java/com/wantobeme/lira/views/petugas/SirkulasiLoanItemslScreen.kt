package com.wantobeme.lira.views.petugas

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.viewmodel.ViewModelFactoryProvider
import com.wantobeme.lira.viewmodel.petugas.SirkulasiLoanItemsViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import dagger.hilt.android.EntryPointAccessors

@Composable
fun provideSirkulasiLoanItemsViewModel(collectionId: String?): SirkulasiLoanItemsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).sirkulasiLoanItemsViewModelFactory()

    return viewModel(factory = SirkulasiLoanItemsViewModel.provideSirkulasiLoanItemsViewModelFactory(factory, collectionId))
}

@Composable
fun SirkulasiLoanItemsScreen(viewModel: SirkulasiLoanItemsViewModel, navController: NavController){

    val loanItems = viewModel.sirkulasiLoanItemsResponse.collectAsState()
    var index by rememberSaveable{ mutableStateOf(0) }
    var showDialog by rememberSaveable{ mutableStateOf(false) }
    var successDialog by rememberSaveable{ mutableStateOf(false) }
    var finishResponse = viewModel.finishPeminjamanResponse.collectAsState()

//    val deleteResponse = viewModel.deleteResponse.collectAsState()

    loanItems.value?.let {
        when(it){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(text = "Terjadi kesalahan pada sistem terkait Detail peminjaman buku.")
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
                            Spacer(modifier = Modifier.size(15.dp))
                            Column(
//                                modifier = Modifier.padding(start = 0.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Nama",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    Text(
                                        text = it.result[0].namaLengkap,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Nomor Identitas",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    Text(
                                        text = it.result[0].nomorIdentitas,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Email",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    Text(
                                        text = it.result[0].email,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Alamat",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    Text(
                                        text = it.result[0].alamat,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Nomor Hp",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    if (it.result[0].noHp != ""){
                                        Text(
                                            text = it.result[0].noHp,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }else{
                                        Text(
                                            text = "-",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
//
                            Spacer(modifier = Modifier.size(10.dp))
                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                items(it.result.size){ item ->
                                    DetailSirkulasiLoanItems(
                                        data = it.result[item],
                                        petugas = true,
                                        finish = {
                                            index = item
                                            showDialog = true
                                        },
                                        extend = {},
                                        onClickCard = {},
                                        onLongClickCard = {
                                            navController.navigate(Screen.Petugas.Pelanggaran.Tambah.route + "/${it.result[item].nomorIdentitas}/${it.result[item].collectionLoanId}/${it.result[item].collectionId}")
                                        },
                                        navController
                                    )
                                    if(showDialog){
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                            },
                                            title = { Text(text = "Selesaikan Peminjaman?") },
                                            text = { Text(text = "Selesaikan peminjaman anggota Koleksi ${it.result[index].collectionId}?") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
                                                    viewModel.finishPeminjaman(it.result[index].collectionLoanId, it.result[index].collectionId)
                                                },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Primary
                                                    )) {
                                                    Text(text = "accept", color = Color.White)
                                                }
                                                OutlinedButton(onClick = {
                                                    showDialog = false
                                                },
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                    )) {
                                                    Text(text = "no", color = Color.Red)
                                                }
                                            }
                                        )
                                    }
                                    if(successDialog){
                                        AlertDialog(
                                            onDismissRequest = {
                                                showDialog = false
                                                navController.navigate(Screen.Petugas.Sirkulasi.Loan.Item.route + "/${it.result[item].collectionLoanId}")
                                            },
                                            title = { Text(text = "Berhasil") },
                                            text = { Text(text = "Peminjaman telah diselesaikan.") },
                                            confirmButton = {
                                                Button(onClick = {
                                                    showDialog = false
                                                    navController.navigate(Screen.Petugas.Sirkulasi.Loan.Item.route + "/${it.result[item].collectionLoanId}")
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
                            if(it.result[0].status.equals("Waiting")){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ){
                                    Button(
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(50.dp)
                                            .padding(top = 10.dp),
                                        onClick = {
                                            // Accept Peminjaman, Update Status to Loaning
                                            it.result.forEach{
                                                viewModel.validasiPeminjaman(it.collectionLoanId)
                                            }
                                            navController.navigate(Screen.Petugas.Sirkulasi.route)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Primary
                                        )
                                    ) {
                                        Text(text = "Accept", color = Color.White)
                                    }
                                    OutlinedButton(
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(50.dp)
                                            .padding(top = 10.dp),
                                        onClick = {
                                            // reject peminjaman, Delete collectionLoan dan collectionLoanItems
                                            it.result.forEach{
                                                viewModel.abortPeminjaman(it.collectionLoanId)
                                            }
                                            navController.navigate(Screen.Petugas.Sirkulasi.route)
                                        },
                                    ) {
                                        Text(text = "Abort", color = Color.Red)
                                    }
                                }
                            }
                        }
                        finishResponse.value?.let {
                            when(it){
                                is Resource.Failure -> {

                                }
                                Resource.Loading -> {
                                    showProgressBar()
                                }
                                is Resource.Success -> {
                                    LaunchedEffect(key1 = Unit){
                                        successDialog=true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if(finishResponse.value!=null){
        BackHandler {
            navController.navigate(Screen.Petugas.Sirkulasi.route)
        }
    }

}

//@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailSirkulasiLoanItems(
    data: SirkulasiLoanItems,
    petugas: Boolean,
    finish: () -> Unit,
    extend: () -> Unit,
    onClickCard: () -> Unit,
    onLongClickCard: () -> Unit,
    navController: NavController
//    @PreviewParameter(SampleLoanItemsProvider::class) data: SirkulasiLoanItems
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .combinedClickable(
                onClick = { },
                onLongClick = {
                    onLongClickCard.invoke()
                }
            )
    ) {
        Column() {
            Text(
                text = "${data.collectionLoanId}/${data.catalogsId}/${data.collectionId}", //collectionLoad_id / collectionid
                style = TextStyle(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Light
                )
            )
            Row(
                modifier = Modifier.padding(15.dp, 12.dp)
            ) {
                if(data.coverURL!=null){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .crossfade(true)
                                .data(data.coverURL)
                                .build(),
                            filterQuality = FilterQuality.High,
                            placeholder = painterResource(id = R.drawable.image_placeholder),
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(70.dp)
                            .height(105.dp)
                            .clip(shape = RoundedCornerShape(3.dp)),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .crossfade(true)
                                .data(R.drawable.no_cover)
                                .build(),
                            filterQuality = FilterQuality.High,
                            placeholder = painterResource(id = R.drawable.image_placeholder),
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(70.dp)
                            .height(105.dp)
                            .clip(shape = RoundedCornerShape(3.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier.padding(7.dp, 0.dp)
                ) {
                    Text(
                        text = data.judul,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = data.penulis,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = data.tahunTerbit,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row() {
                        Text(
                            text = "loan: ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Thin
                            )
                        )
                        Text(
                            text = data.tanggalPinjam,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Row() {
                        // if extend day != null get extend day
                        // else due date
                        Text(
                            text = "exp: ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Thin
                            )
                        )
                        Text(
                            text = data.tanggalBatasPinjam,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    if(data.tanggalDikembalikan!="-"){
                        Row() {
                            Text(
                                text = "return: ",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Thin
                                )
                            )
                            Text(
                                text = data.tanggalDikembalikan!!,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        if(data.terlambat!! > 0){
                            Row() {
                                Text(
                                    text = "Terlambat: ",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Text(
                                    text = data.terlambat.toString()!! + " Hari",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color.Red
                                )
                            }
                        }else{
                            Row() {
                                Text(
                                    text = "Keterlambatan: ",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Text(
                                    text = "Tidak ada",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Primary
                                )
                            }
                        }

                    }
                    else if(!data.status.equals("Return")){
                        Row() {
                            Text(
                                text = "Status: ",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Thin
                                )
                            )
                            Text(
                                text = data.status,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color.Blue
                            )
                        }
                    }
                }
            }
            // jika petugas
            if(petugas){
                if(data.status.equals("Loaning")){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Button(
                            modifier = Modifier
                                .width(120.dp)
                                .height(35.dp),
                            onClick = {
                                // update status to return
                                finish.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Primary
                            )
                        ) {
                            Text(text = "Finish", color = Color.White)
                        }
                    }
                }else{
                    // jika koleksi dikembalikan terlambat
                    if(data.terlambat!! > 0){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Button(
                                onClick = {
                                    navController.navigate(Screen.Petugas.Pelanggaran.Tambah.route + "/${data.nomorIdentitas}/${data.collectionLoanId}/${data.collectionId}")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "+ denda",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal
                                    ),
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    else{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            Text(
                                text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }

                }
            }
            // jika anggota
            else{
                // tambah tombol extend days
                if(data.status.equals("Loaning")){
                    if(data.isextended!! > 0){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Button(
                                enabled = false,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(35.dp),
                                onClick = {
                                    // Perpanjang Peminjaman
                                    extend.invoke()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Primary
                                )
                            ) {
                                Text(text = "Extend", color = Color.White)
                            }
                        }
                    }else{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Button(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(35.dp),
                                onClick = {
                                    // Perpanjang Peminjaman
                                    extend.invoke()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Primary
                                )
                            ) {
                                Text(text = "Extend", color = Color.White)
                            }
                        }
                    }

                }
                else{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.End
                    ){
                        Text(
                            text = "B-${data.nomorBarcode}", //collectionLoad_id / collectionid
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }
        }

    }
}

class SampleLoanItemsProvider: PreviewParameterProvider<SirkulasiLoanItems> {
    override val values = sequenceOf(
        SirkulasiLoanItems(
            "NING JAUHAROTUL WAHIDIYAH",
            "201964010066",
            "Malang",
            "jauhawahidah@gmail.com",
            "",
            "122041200002",
            "2022-04-12 00:00:00",
            "2022-04-21 00:00:00",
            0,
            "2022-04-12 00:00:00",
            0,
            "Return",
            "5785",
            "9105",
            "20180011",
            "Metodologi Riset",
            "Marzuki ",
            "1989",
            "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/5785_result.webp?alt=media&token=ebbe4249-fd7b-4326-ba56-6d24a4ce120d"
        ),
        SirkulasiLoanItems(
            "NING JAUHAROTUL WAHIDIYAH",
            "201964010066",
            "malang",
            "jauhawahidah@gmail.com",
            "",
            "122041200002",
            "2022-04-12 00:00:00",
            "2022-04-21 00:00:00",
            0,
            "2022-04-12 00:00:00",
            0,
            "Return",
            "5794",
            "9115",
            "20180021",
            "Metodologi Penelitian Kualitatif - Kuantitatif",
            "Kasiram ",
            "2010",
            "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/5794_result.webp?alt=media&token=cf2f044f-cbf1-4ef1-8e59-1100b03f640e"
        ),

    )
}

