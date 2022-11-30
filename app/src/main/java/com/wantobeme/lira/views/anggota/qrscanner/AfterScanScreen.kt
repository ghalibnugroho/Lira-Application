package com.wantobeme.lira.views.anggota.qrscanner

import android.util.Log
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.Loaning
import com.wantobeme.lira.model.SirkulasiLoanItems
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.viewmodel.anggota.QRScanViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.AnggotaActivity
import com.wantobeme.lira.views.petugas.SampleLoanItemsProvider
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import com.wantobeme.lira.views.utils.startNewActivity
import kotlinx.coroutines.delay

@Composable
fun AfterScanScreen(qrScanViewModel: QRScanViewModel, navController: NavController){

    val context = LocalContext.current

    val currentArgs = navController.currentBackStackEntry?.arguments?.getString("kodeQR")
    Log.i("currentArgs AfterScan","${currentArgs}")

    var showWarningDialog by rememberSaveable{ mutableStateOf(false) }

    var memberNo = qrScanViewModel.myMemberNo

    var afterScan = qrScanViewModel.afterScan.collectAsState()

    var successDialog by rememberSaveable{ mutableStateOf(false) }
    var abortDialog by rememberSaveable{ mutableStateOf(false) }


    // kondisi qr untuk presensi
    if(currentArgs.equals("PerpustakaanUNIRA")){
        Text("Input data presensi")
    }
    else{
        // launch viewmodel untuk mengecek ketersediaan kode-QR pada koleksi
        LaunchedEffect(key1 = ScannerActivity::class){
            qrScanViewModel.qrCheck(currentArgs)
            qrScanViewModel.getMemberNo()
        }
        val qrScan = qrScanViewModel.qrScan.collectAsState()
        // menjabarkan hasil response check kode-QR dari server
        qrScan.value?.let {
            when(it){
                is Resource.Failure -> {
                    LaunchedEffect(key1 = Unit){
                        showWarningDialog = true
                    }
                }
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    if(it.result.status == 0){
                        LaunchedEffect(key1 = Unit){
                            showWarningDialog = true
                        }
                    }else{
                        loaningScreen(
                            data = it.result,
                            acceptButton = {
                                qrScanViewModel.pinjamBuku(it.result.collectionId, memberNo.identitas)
                            },
                            cancelButton = {
                                navController.navigate(Screen.Anggota.QRScanner.route)
                            }
                        )
                    }
                }
            }
        }

        afterScan.value?.let {
            when(it){
                is Resource.Failure -> TODO()
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    if(it.result.status ==1){
                        LaunchedEffect(key1 = Unit ){
                            successDialog=true
                        }
                    }else if(it.result.status ==0){
                        LaunchedEffect(key1 = Unit ){
                            abortDialog=true
                        }
                    }
                }
            }
        }

        if(successDialog){
            AlertDialog(
                onDismissRequest = {showWarningDialog = false
                    context.startNewActivity(AnggotaActivity::class.java)
                },
                title = {
                    Text(text = "Berhasil")
                },
                text = {
                    Text(text = "Tambah lagi atau anda bisa menemui petugas untuk verifikasi")
                },
                confirmButton = {
                    Button(onClick = { showWarningDialog = false
                        navController.navigate(Screen.Anggota.QRScanner.route)
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Primary
                        )) {
                        Text(text = "Scan kembali", color = Color.White)
                    }
                }
            )
        }

        if(abortDialog){
            AlertDialog(
                onDismissRequest = {showWarningDialog = false
                    navController.navigate(Screen.Anggota.QRScanner.route)
                },
                title = {
                    Text(text = "Abort")
                },
                text = {
                    Text(text = "Buku masih dalam proses/sedang dipinjam")
                },
                confirmButton = {
                    Button(onClick = { showWarningDialog = false
                        navController.navigate(Screen.Anggota.QRScanner.route)
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        )) {
                        Text(text = "Scan Kembali", color = Color.White)
                    }
                }
            )
        }


        if(showWarningDialog){
            AlertDialog(
                onDismissRequest = {showWarningDialog = false
                    navController.navigate(Screen.Anggota.QRScanner.route)
                },
                title = {
                    Text(text = "Perhatian")
                },
                text = {
                    Text(text = "Kode-QR Anda Salah, Ulangi dengan benar!")
                },
                confirmButton = {
                    Button(onClick = { showWarningDialog = false
                        navController.navigate(Screen.Anggota.QRScanner.route)
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        )) {
                        Text(text = "Kembali", color = Color.White)
                    }
                }
            )
        }
    }
    BackHandler {
        navController.navigate(Screen.Anggota.QRScanner.route)
    }
}


//@Preview(showBackground = true)
@Composable
fun loaningScreen(
    data: Loaning,
//    @PreviewParameter (SampleLoaningProvider::class) data: Loaning,
    acceptButton: () -> Unit,
    cancelButton: () -> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Literasi Raden",
                style = TextStyle(
                    fontFamily = ranchoFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 46.sp
                )
            )
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column {
                    Spacer(modifier = Modifier.size(30.dp))
                    if(data.coverURL != null){
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
                                .width(140.dp)
                                .height(210.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
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
                                .width(140.dp)
                                .height(210.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Column{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Column{
                            Text("Judul:")
                            Text(text = data.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Column{
                            Text("Penulis:")
                            Text(text = data.author,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Column{
                            Text("Tahun:")
                            Text(text = data.publishYear,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Column{
                            Text("Penerbit:")
                            Text(text = data.publisher,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Column{
                            Text("Kota:")
                            Text(text = data.publishLocation,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Column{
                            Text("Nomor Koleksi:")
                            Text(text = data.callNumber,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = "Pinjam?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            Row{
                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                        .padding(top = 10.dp),
                    onClick = {
                        acceptButton.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Primary
                    )
                ) {
                    Text(text = "Pinjam", color = Color.White)
                }
                Spacer(modifier = Modifier.size(30.dp))
                OutlinedButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                        .padding(top = 10.dp),
                    onClick = {
                        cancelButton.invoke()
                    }
                ) {
                    Text(text = "Tidak", color = Color.Red)
                }
            }

        }
    }
}

class SampleLoaningProvider: PreviewParameterProvider<Loaning> {
    override val values = sequenceOf(
        Loaning(
           1,
            "7941",
            "Dasar Dasar Psikometri",
            "Azwar Saifuddin ",
            "2015",
            "Pustaka Pelajar ",
            "Yogyakarta ",
            "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/7941_result.webp?alt=media&token=ddf39c20-51f5-45ac-b2d1-c1362af37f40",
            "12473",
            "2022112301",
            "155.25 SAI d C.4"
        )
    )
}
